package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.supplychain.PoLines;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.LookupEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import org.springframework.stereotype.Component;

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

    private final String PO_HEADER_FILE_NAME = "PoHeadersInterfaceOrder.csv";
    private final String PO_LINE_FILE_NAME = "PoLinesInterfaceOrder.csv";
    private final String PO_LINE_LOCATION_FILE_NAME = "PoLineLocationsInterfaceOrder.csv";
    private final String PO_DISTRIBUTION_FILE_NAME = "PoDistributionsInterfaceOrder.csv";

    public SupplyChainPOProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
        super(fbdiFormatService);
        this.fbdiFormatService = fbdiFormatService;
        this.referenceDataService = referenceDataService;
    }

    @Override
    String getSourceSystem() {
        return "AS400";
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

    @Override
    public TargetSystemResponse transform(SourceSystemRequest request, String batchId) {
        POHeaderResponse poHeaderResponse = new POHeaderResponse();
        if (request instanceof SupplyChainPORequest) {
            SupplyChainPORequest input = (SupplyChainPORequest) request;
            transformPoHeader(input, batchId, poHeaderResponse);
            transformPoLine(input, poHeaderResponse);
        }
        return poHeaderResponse;
    }

    private void transformPoDistribution(PoLines poLine, POLineLocationResponse poLineLocationResponse, POHeaderResponse headerResponse) {

        List<PODistributionResponse> poDistributionResponses = new ArrayList<>();

        PODistributionResponse poDistributionResponse = new PODistributionResponse();
        poDistributionResponse.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
        poDistributionResponse.setInterfaceDistributionKey(getRandomNumber());
        poDistributionResponse.setDistribution(poLineLocationResponse.getSchedule());//TODO check again Use same Line number# which you referred while creating Line csv file
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
        poLineLocationResponse.setSchedule(lineResponse.getLine()); //TODO check again Use same Line number# which you referred while creating Line csv file
        poLineLocationResponse.setShipToLocation(referenceDataService.getShipToLocation(String.valueOf(lineRequest.getWhseNo()), LookupEnum.SC_PO_DEF_SHIP_TO_LOC_CODE));
        poLineLocationResponse.setShipToOrganization(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_SHIP_TO_ORG.name()));

        poLineLocationResponse.setQuantity(lineResponse.getQuantity());//TODO check logic
        poLineLocationResponse.setNeedByDate(getNeedByDate(lineRequest.getDueDate()));
        poLineLocationResponse.setDestinationTypeCode(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_DEST_TYPE_CODE.name()));
        poLineLocationResponse.setAccrueAtReceipt(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_ACCR_AT_RECPT.name()));
        poLineLocationResponse.setRequestedDeliveryDate(dateConvertor(lineRequest.getDueDate()));

        lineLocations.add(poLineLocationResponse);
        lineResponse.setPoLineLocationResponses(lineLocations);
        transformPoDistribution(lineRequest, poLineLocationResponse, headerResponse);
    }

    private String getNeedByDate(Date dueDate) {
        return LocalDate.ofInstant(
                dueDate.toInstant(), ZoneId.systemDefault()).isAfter(LocalDate.now())
                ? dateConvertor(dueDate)
                : dateConvertor(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

    }

    private void transformPoLine(SupplyChainPORequest requestHeader, POHeaderResponse headerResponse) {
        List<POLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;
        for (PoLines poLine : requestHeader.getPoLines()) {
            lineNum = lineNum + 1;
            POLineResponse poLineResponse = new POLineResponse();
            poLineResponse.setInterfaceHeaderKey(headerResponse.getInterfaceHeaderKey());
            poLineResponse.setInterfaceLineKey(getRandomNumber());
            poLineResponse.setAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_ACTION.name()));
            poLineResponse.setLine(String.valueOf(lineNum)); //TODO UPDATE how to get next line number
            poLineResponse.setLineType(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_TYPE.name()));

            poLineResponse.setItemDescription(poLine.getItemDesc());
            poLineResponse.setCategoryName(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_CATEGORY.name()));//CategoryName
            Integer quantity = poLine.getOnOrder();
            String uom = "CS";
            Double price = Double.valueOf(poLine.getOnOrder());

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

            poLineResponse.setPrice(doubleToString(price)); //TODO check point 3
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

    private void transformPoHeader(SupplyChainPORequest request, String batchId, POHeaderResponse response) {
        response.setInterfaceHeaderKey(getRandomNumber());
        response.setAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_ACTION.name()));//TODO UPDATE check if new or update
        response.setBatchID(batchId);

        response.setImportSource(referenceDataService.getLookupByCode(LookupEnum.SC_PO_IMPORT_SOURCE.name()));
        response.setApprovalAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_APPROVAL_ACTION.name()));
        response.setOrder("");//TODO UPDATE oracle po number
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
        }
        String paymentTerms = getPaymentTerms(request);
        response.setPaymentTerms(paymentTerms);


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
        String paymentTerms = "";
        if (!poHeader.getTermsNetDays().equalsIgnoreCase("0")) {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + " ,NET, " + poHeader.getTermsNetDays();
        } else {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + ",NET," + 30;
        }
        return paymentTerms;
    }

}
