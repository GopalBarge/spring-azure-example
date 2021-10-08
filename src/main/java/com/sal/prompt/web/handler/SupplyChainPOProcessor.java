package com.sal.prompt.web.handler;

import com.sal.prompt.web.client.rest.RestClient;
import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.supplychain.PoLines;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.LookupEnum;
import com.sal.prompt.web.model.OpenPO;
import com.sal.prompt.web.model.POTypesEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import com.sal.prompt.web.utils.InterfaceEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.sal.prompt.web.utils.CommonUtility.doubleToString;

@Component
public class SupplyChainPOProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;
    private final RestClient restClient;

    private final String PO_HEADER_FILE_NAME = "PoHeadersInterfaceOrder.csv";
    private final String PO_LINE_FILE_NAME = "PoLinesInterfaceOrder.csv";
    private final String PO_LINE_LOCATION_FILE_NAME = "PoLineLocationsInterfaceOrder.csv";
    private final String PO_DISTRIBUTION_FILE_NAME = "PoDistributionsInterfaceOrder.csv";

    public SupplyChainPOProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService, RestClient restClient) {
        super(fbdiFormatService);
        this.fbdiFormatService = fbdiFormatService;
        this.referenceDataService = referenceDataService;
        this.restClient = restClient;
    }

    @Override
    String getSourceSystem() {
        return InterfaceEnum.AS400_PO.name();
    }

    @Override
    String getTargetSystem() {
        return "Oracle";
    }


    private String getRandomNumber() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Override
    protected Map<String, List> getFileDataMap(List<TargetSystemResponse> data) {
        Map<String, List> fileDateMap = new HashMap<>(4);
        List<POHeaderResponse> transformedData = data.stream().map(d -> (POHeaderResponse) d).collect(Collectors.toList());
        List<POLineResponse> poLineResponses = transformedData.stream().flatMap(e -> e.getPoLineResponses().stream()).sorted(Comparator.comparing(POLineResponse::getInterfaceHeaderKey)).collect(Collectors.toList());
        List<POLineLocationResponse> poLineLocationResponses = poLineResponses.stream().flatMap(e -> e.getPoLineLocationResponses().stream()).sorted(Comparator.comparing(POLineLocationResponse::getInterfaceLineKey)).collect(Collectors.toList());
        List<PODistributionResponse> poDistributionResponses = poLineLocationResponses.stream().flatMap(e -> e.getPoDistributionResponses().stream()).sorted(Comparator.comparing(PODistributionResponse::getInterfaceLineLocationKey)).collect(Collectors.toList());

        fileDateMap.put(PO_HEADER_FILE_NAME, transformedData);
        fileDateMap.put(PO_LINE_FILE_NAME, poLineResponses);
        fileDateMap.put(PO_LINE_LOCATION_FILE_NAME, poLineLocationResponses);
        fileDateMap.put(PO_DISTRIBUTION_FILE_NAME, poDistributionResponses);
        return fileDateMap;
    }

    private boolean isExistingPOLines(POHeaderResponse poHeaderResponse) {

        if (!poHeaderResponse.isUpdate()) {
            return false;
        } else {
            poHeaderResponse.getPoLineResponses().stream().filter(line -> line.isUpdate()).findAny().isPresent();
        }
        boolean isExisting = false;

        return false;
    }

    @Override
    public TargetSystemResponse transform(SourceSystemRequest request, String batchId) {
        POHeaderResponse poHeaderResponse = new POHeaderResponse();
        if (request instanceof SupplyChainPORequest) {
            SupplyChainPORequest input = (SupplyChainPORequest) request;
            transformPoHeader(input, batchId, poHeaderResponse);
            transformPoLine(input, poHeaderResponse);
        }

        if (!poHeaderResponse.isUpdate()) {
            return poHeaderResponse;
        }

        List<POLineResponse> newLines = poHeaderResponse.getPoLineResponses().stream().filter(line -> !line.isUpdate()).collect(Collectors.toList());
        List<POLineResponse> updatedLines = poHeaderResponse.getPoLineResponses().stream().filter(line -> line.isUpdate()).collect(Collectors.toList());

        if (updatedLines.isEmpty()) {
            return poHeaderResponse;
        }
        if (!newLines.isEmpty()) {
            poHeaderResponse.setPoLineResponses(newLines);
        }
        if (!updatedLines.isEmpty()) {
            try {
                updatePoLinesWithOracleAPI(poHeaderResponse, updatedLines);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return poHeaderResponse;
    }


    private void transformPoDistribution(PoLines poLine, POLineLocationResponse poLineLocationResponse, POHeaderResponse headerResponse) {

        List<PODistributionResponse> poDistributionResponses = new ArrayList<>();

        PODistributionResponse poDistributionResponse = new PODistributionResponse();
        poDistributionResponse.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
        poDistributionResponse.setInterfaceDistributionKey(getRandomNumber());
        poDistributionResponse.setDistribution("1");//always 1
        poDistributionResponse.setRequester(headerResponse.getBuyer());

        poDistributionResponse.setQuantity(poLineLocationResponse.getQuantity());
        poDistributionResponse.setChargeAccountSegment1(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DISTI_SEGMENT1.name()));
        poDistributionResponse.setChargeAccountSegment2(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DISTI_SEGMENT2.name()));
        poDistributionResponse.setChargeAccountSegment3(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DISTI_SEGMENT3.name()));
        poDistributionResponse.setChargeAccountSegment4(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DISTI_SEGMENT4.name()));
        poDistributionResponse.setChargeAccountSegment5(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DISTI_SEGMENT5.name()));
        poDistributionResponse.setChargeAccountSegment6(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DISTI_SEGMENT6.name()));
        poDistributionResponse.setChargeAccountSegment7(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DISTI_SEGMENT7.name()));

        poDistributionResponses.add(poDistributionResponse);
        poLineLocationResponse.setPoDistributionResponses(poDistributionResponses);
    }

    private void transformPoLineLocation(PoLines lineRequest, POLineResponse lineResponse, POHeaderResponse headerResponse) {

        List<POLineLocationResponse> lineLocations = new ArrayList<>();
        POLineLocationResponse poLineLocationResponse = new POLineLocationResponse();
        poLineLocationResponse.setInterfaceLineKey(lineResponse.getInterfaceLineKey());
        poLineLocationResponse.setInterfaceLineLocationKey(getRandomNumber());
        poLineLocationResponse.setSchedule("1");//always 1
        poLineLocationResponse.setShipToLocation(referenceDataService.getShipToLocation(String.valueOf(lineRequest.getWhseNo()), LookupEnum.SC_PO_DEF_SHIP_TO_LOC_CODE));
        poLineLocationResponse.setShipToOrganization(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_SHIP_TO_ORG.name()));

        poLineLocationResponse.setQuantity(lineResponse.getQuantity());
        poLineLocationResponse.setNeedByDate(getNeedByDate(lineRequest.getDueDate()));
        poLineLocationResponse.setDestinationTypeCode(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_DEST_TYPE_CODE.name()));
        poLineLocationResponse.setAccrueAtReceipt(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_ACCR_AT_RECPT.name()));
        poLineLocationResponse.setRequestedDeliveryDate(dateConvertor(lineRequest.getDueDate()));

        lineLocations.add(poLineLocationResponse);
        lineResponse.setPoLineLocationResponses(lineLocations);
        transformPoDistribution(lineRequest, poLineLocationResponse, headerResponse);
    }

    private void transformPoHeader(SupplyChainPORequest request, String batchId, POHeaderResponse response) {
        Optional<OpenPO> openPo = getOpenPO(request);
        boolean isUpdate = openPo.isPresent();
        response.setUpdate(isUpdate);
        String action = isUpdate ? referenceDataService.getLookupByCode(LookupEnum.SC_PO_ACTION_UPDATE.name()) : referenceDataService.getLookupByCode(LookupEnum.SC_PO_ACTION.name());
        String order = isUpdate ? openPo.get().getDocumentNumber() : null;

        String changeOrderDescription = isUpdate ? request.getDataColumn() : null;
        if (isUpdate && Objects.isNull(changeOrderDescription)) {
            changeOrderDescription = referenceDataService.getLookupByCode(LookupEnum.SC_PO_CHANGE_ORDER_DESC.name());
        }

        response.setInterfaceHeaderKey(getRandomNumber());
        response.setAction(action);
        response.setBatchID(batchId);
        response.setImportSource(referenceDataService.getLookupByCode(LookupEnum.SC_PO_IMPORT_SOURCE.name()));
        response.setApprovalAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_APPROVAL_ACTION.name()));
        response.setOrder(order);
        response.setDocumentTypeCode(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DOC_TYPE_CODE.name()));
        response.setProcurementBU(referenceDataService.getLookupByCode(LookupEnum.SC_PO_PRC_BU.name()));
        response.setRequisitioningBU(referenceDataService.getLookupByCode(LookupEnum.SC_PO_REQ_BU.name()));
        response.setSoldToLegalEntity(referenceDataService.getLookupByCode(LookupEnum.SC_PO_SOLD_TO_LEGAL_ENTITY.name()));
        response.setBillToBU(referenceDataService.getLookupByCode(LookupEnum.SC_PO_BILL_BU.name()));
        response.setBuyer(referenceDataService.getBuyer(request.getBuyer(), LookupEnum.SC_PO_DEF_BUYER));
        response.setCurrencyCode(referenceDataService.getLookupByCode(LookupEnum.SC_PO_CURRENCY_CODE.name()));
        response.setBillToLocation(referenceDataService.getLookupByCode(LookupEnum.SC_PO_BILL_TO_LOC.name()));

        String shipToLocation = referenceDataService.getShipToLocation(String.valueOf(request.getWhseNo()), LookupEnum.SC_PO_DEF_SHIP_TO_LOC_CODE);
        response.setShipToLocation(shipToLocation);
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(request.getVendorNbr());
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            response.setSupplier(supplier.getSupplierName());
            response.setSupplierNumber(supplier.getSupplierNumber());
            response.setSupplierSite(supplier.getSupplierSiteCode());
        } else {
            // error vender nbr not found
        }

        String paymentTerms = getPaymentTerms(request);//TODO check exact format
        response.setPaymentTerms(paymentTerms);

        response.setChangeOrderDescription(changeOrderDescription);
        response.setRequiredAcknowledgment(referenceDataService.getLookupByCode(LookupEnum.SC_PO_REQU_ACK.name()));
        response.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.SC_PO_ATTRIBUTE_CAT.name()));
        response.setAttribute1(request.getBuyer());
        response.setAttribute2(request.getBuyerName());
        response.setAttribute3(request.getPoNbr());
        response.setAttribute4(request.getVendorFac());
        response.setAttribute5(request.getVendFacName());
        response.setAttribute6(request.getVendorNbr());
        response.setAttribute7(String.valueOf(request.getOrderedQty()));
        response.setAttribute8(String.valueOf(request.getOrderedWeight()));
        response.setAttribute9(request.getFreightAllow());
        response.setAttribute10(request.getBackhaul());
        response.setAttribute11(request.getFlagBackhaul());
        response.setAttribute12(request.getFreightBillFlag());
        response.setAttribute13(request.getPickupCost());
        response.setAttribute15(request.getDefaultFbcCost());
        response.setAttribute16(request.getMasterNumber());
        response.setAttributeDate1(dateConvertor(request.getDateOrdered()));
        response.setAttributeDate2(dateConvertor(request.getDatePickup()));
        response.setAttributeDate3(dateConvertor(request.getDateReceived()));
    }

    private void transformPoLine(SupplyChainPORequest requestHeader, POHeaderResponse headerResponse) {
        List<POLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;
        for (PoLines poLine : requestHeader.getPoLines()) {
            lineNum = lineNum + 1;
            boolean isUpdate = false;
            if (headerResponse.isUpdate()) {
                Optional<OpenPO> existingPOLine = getExistingPoLine(requestHeader.getPoNbr(), poLine.getCaseUpc(), poLine.getItemNbr());
                isUpdate = existingPOLine.isPresent();
            }
            String lineNumber = isUpdate ? getNextLineNumber(requestHeader) : String.valueOf(lineNum);
            POLineResponse poLineResponse = new POLineResponse();
            poLineResponse.setUpdate(isUpdate);
            poLineResponse.setInterfaceHeaderKey(headerResponse.getInterfaceHeaderKey());
            poLineResponse.setInterfaceLineKey(getRandomNumber());
            poLineResponse.setAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_ACTION.name()));

            poLineResponse.setLine(lineNumber);
            poLineResponse.setLineType(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_TYPE.name()));

            poLineResponse.setItemDescription(poLine.getItemDesc());
            poLineResponse.setCategoryName(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_CATEGORY.name()));//CategoryName
            Integer quantity = poLine.getOnOrder();
            String uom = "CS";
            Double price = Double.valueOf(poLine.getListCost());

            if ("LBS".equalsIgnoreCase(poLine.getItemSize()) ||
                    "LB AVG".equalsIgnoreCase(poLine.getItemSize())) {

                quantity = poLine.getOnOrder() * poLine.getItemPack();
                uom = "LBS";
                if (poLine.getListCost() > 7) {
                    price = (poLine.getListCost() * 1.0) / poLine.getItemPack();
                } else {
                    price = Double.valueOf(poLine.getListCost());
                }
            }
            poLineResponse.setQuantity(String.valueOf(quantity));
            poLineResponse.setUom(uom);

            poLineResponse.setPrice(doubleToString(price));
            poLineResponse.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_ATTRIBUTE_CAT.name()));
            poLineResponse.setAttribute1(poLine.getItemFacility());
            poLineResponse.setAttribute2(poLine.getItemNbr());
            poLineResponse.setAttribute3(String.valueOf(poLine.getPalletQuantity()));
            poLineResponse.setAttribute4(poLine.getItemSize());
            poLineResponse.setAttribute5(String.valueOf(poLine.getWeight()));
            poLineResponse.setAttribute6(poLine.getItemCube());
            poLineResponse.setAttribute7(poLine.getItemDept());
            poLineResponse.setAttribute8(poLine.getCaseUpc());
            poLineResponse.setAttribute9(poLine.getUpcFormatCd());
            poLineResponse.setAttribute10(String.valueOf(poLine.getItemPack()));
            poLineResponse.setAttribute11(poLine.getAmtFreightBill());
            poLineResponse.setAttribute12(poLine.getAmtBackhaul());
            poLineResponse.setAttribute13(String.valueOf(poLine.getLastCost()));
            poLineResponse.setAttribute14(String.valueOf(poLine.getAmountOffInvoice()));
            poLineResponses.add(poLineResponse);
            transformPoLineLocation(poLine, poLineResponse, headerResponse);
        }
        headerResponse.setPoLineResponses(poLineResponses);

    }

    private String getNeedByDate(Date dueDate) {
        return LocalDate.ofInstant(
                dueDate.toInstant(), ZoneId.systemDefault()).isAfter(LocalDate.now())
                ? dateConvertor(dueDate)
                : dateConvertor(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

    }

    String getNextLineNumber(SupplyChainPORequest requestHeader) {
        OptionalInt maxLineNumber = referenceDataService.getOpenPos(requestHeader.getPoNbr(), POTypesEnum.AS400_PO)
                .stream()
                .mapToInt(po -> Integer.parseInt(po.getLineNum())).max();
        int max = maxLineNumber.isPresent() ? maxLineNumber.getAsInt() : 0;
        return String.valueOf(max + 1);
    }

    private Optional<OpenPO> getExistingPoLine(String poNumber, String caseUPC, String itemNumber) {
        return referenceDataService.getOpenPos(poNumber, POTypesEnum.AS400_PO).stream().filter(po ->
                po.getItemUpc().equalsIgnoreCase(caseUPC)
                        && po.getItemNbr().equalsIgnoreCase(itemNumber)

        ).findAny();
    }

    private Optional<OpenPO> getOpenPO(SupplyChainPORequest requestHeader) {
        List<OpenPO> openPOs = referenceDataService.getOpenPos(requestHeader.getPoNbr(), POTypesEnum.AS400_PO);
        Optional<OpenPO> openPo = openPOs.stream().filter(po ->
                        po.getVendorFac().equalsIgnoreCase(requestHeader.getVendorFac())
                                && po.getVendorNbr().equalsIgnoreCase(requestHeader.getVendorNbr())

                // && po.getDateOrdered().equalsIgnoreCase(requestHeader.getDateOrdered()) //TODO order missing form oracle PO reports

        ).findAny();
        return openPo;
    }

    private void updatePoLinesWithOracleAPI(POHeaderResponse poHeaderResponse, List<POLineResponse> updatedLines) throws Exception {
        POUpdateDTO updateDTO = new POUpdateDTO();

        updateDTO.setPaymentTerms(poHeaderResponse.getPaymentTerms());
        updateDTO.setChangeOrderDescription(poHeaderResponse.getChangeOrderDescription());
        List<PurchaseOrderEntryLineDTO> lines = new ArrayList<>();
        updateDTO.setPurchaseOrderEntryLines(lines);
        updatedLines.stream().forEach(line -> {
            String poNumber = line.getAttributeNumber2();
            String itemNumber = line.getAttributeNumber3();
            String caseUpc = line.getAttributeNumber8();
            Optional<OpenPO> existingLine = getExistingPoLine(poNumber, caseUpc, itemNumber);
            if (existingLine.isPresent()) {
                OpenPO oracleLine = existingLine.get();
                updateDTO.setPOHeaderId(oracleLine.getPoHeaderId());
                PurchaseOrderEntryLineDTO updateLine = new PurchaseOrderEntryLineDTO();
                updateLine.setAction("CHANGE");
                updateLine.setLineNumber(oracleLine.getLineNum());
                updateLine.setPrice(line.getPrice());
                updateLine.setQuantity(line.getQuantity());
                lines.add(updateLine);
            }
        });

        restClient.executeSoapRequest(buildUpdatePoLinePayload(updateDTO));

    }

    private String buildUpdatePoLinePayload(POUpdateDTO updateDTO) {
        StringBuilder payload = new StringBuilder();
        payload.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:typ=\"http://xmlns.oracle.com/apps/prc/po/editDocument/purchaseOrderServiceV2/types/\"\n" +
                "                  xmlns:pur=\"http://xmlns.oracle.com/apps/prc/po/editDocument/purchaseOrderServiceV2/\"\n" +
                "                  xmlns:draf=\"http://xmlns.oracle.com/apps/prc/po/editDocument/flex/draftPurchaseOrderDistribution/\"\n" +
                "                  xmlns:pjc=\"http://xmlns.oracle.com/apps/prc/po/commonPo/flex/PJCPoDraftDistribution/\"\n" +
                "                  xmlns:draf1=\"http://xmlns.oracle.com/apps/prc/po/editDocument/flex/draftPurchasingDocumentSchedule/\"\n" +
                "                  xmlns:draf2=\"http://xmlns.oracle.com/apps/prc/po/editDocument/flex/draftPurchasingDocumentLine/\"\n" +
                "                  xmlns:draf3=\"http://xmlns.oracle.com/apps/prc/po/editDocument/flex/draftPurchasingDocumentHeader/\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <typ:changePurchaseOrderAsync>\n" +
                "            <typ:changeOrderEntry>\n" +
                "                <pur:POHeaderId>" + updateDTO.getPOHeaderId() + "</pur:POHeaderId>\n" +
                "                <pur:ChangeOrderDescription>" + updateDTO.getChangeOrderDescription() + "</pur:ChangeOrderDescription>\n" +
                "                <pur:PaymentTerms>" + updateDTO.getPaymentTerms() + "</pur:PaymentTerms>\n");

        updateDTO.getPurchaseOrderEntryLines().stream().forEach(line -> {
            payload.append(
                    "\n" +
                            "                <pur:PurchaseOrderEntryLine>\n" +
                            "                    <pur:Action>" + line.getAction() + "</pur:Action>\n" +
                            "                    <pur:LineNumber>" + line.getLineNumber() + "</pur:LineNumber>\n" +
                            "                    <pur:Price currencyCode=\"USD\">" + line.getPrice() + "</pur:Price>\n" +
                            "                    <pur:Quantity>" + line.getQuantity() + "</pur:Quantity>\n" +
                            "                </pur:PurchaseOrderEntryLine>\n");
        });


        payload.append("\n" +
                "            </typ:changeOrderEntry>\n" +
                "        </typ:changePurchaseOrderAsync>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>");

        return payload.toString();
    }

    private String dateConvertor(Date value) {
        //change date to string on dd-MM-yyyy format e.g. "20-02-2017"
        SimpleDateFormat dateformater = new SimpleDateFormat("yyyy/MM/dd");
        String result = dateformater.format(value);
        return result;
    }

    private String intToStringConvertor(Integer value) {
        String result = Integer.toString(value);
        return result;
    }

    private String getPaymentTerms(SupplyChainPORequest poHeader) {
        String paymentTerms = "IMMEDIATE";
        if (!poHeader.getTermsNetDays().equalsIgnoreCase("0")) {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + " ,NET, " + poHeader.getTermsNetDays();
        } else {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + ",NET," + 30;
        }
        return paymentTerms;
    }

}
