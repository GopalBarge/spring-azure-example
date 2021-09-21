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

    @Override
    String getBatchId() {
        return String.valueOf(System.currentTimeMillis());
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
//            transformPoLineLocation(input.getPOHeader(), poHeaderResponse);
//            transformPoDistribution(input.getPOHeader(), poHeaderResponse);
        }
        return poHeaderResponse;
    }

    private void transformPoDistribution(PoLines poLine, POLineLocationResponse poLineLocationResponse) {

        List<PODistributionResponse> poDistributionResponses = new ArrayList<>();

        PODistributionResponse poDistributionResponse = new PODistributionResponse();
        poDistributionResponse.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
        poDistributionResponse.setInterfaceDistributionKey(getRandomNumber());
        poDistributionResponse.setDistribution(poLineLocationResponse.getSchedule());//Use same Line number# which you referred while creating Line csv file
        //poDistributionResponse.setRequester(poDistributionResponse.getBu);//TODO check logic

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

    private void transformPoLineLocation(PoLines lineRequest, POLineResponse lineResponse) {

        List<POLineLocationResponse> lineLocations = new ArrayList<>();
        POLineLocationResponse poLineLocationResponse = new POLineLocationResponse();
        poLineLocationResponse.setInterfaceLineKey(lineResponse.getInterfaceLineKey());
        poLineLocationResponse.setInterfaceLineLocationKey(getRandomNumber());
        poLineLocationResponse.setSchedule(lineResponse.getLine()); //Use same Line number# which you referred while creating Line csv file
//        poLineLocationResponse1.setShipToLocation(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_SHIP_TO_LOC.name()));//TODO ShipToLocation
        poLineLocationResponse.setShipToOrganization(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_SHIP_TO_ORG.name()));
        //poLineLocationResponse.setAmount(lineRequest.getA);//TODO check amount field

        poLineLocationResponse.setQuantity(lineResponse.getQuantity());
        poLineLocationResponse.setNeedByDate(getNeedByDate(lineRequest.getDueDate()));
        poLineLocationResponse.setDestinationTypeCode(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_DEST_TYPE_CODE.name()));
        poLineLocationResponse.setAccrueAtReceipt(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_LOC_ACCR_AT_RECPT.name()));
        poLineLocationResponse.setRequestedDeliveryDate(dateConvertor(lineRequest.getDueDate()));

        lineLocations.add(poLineLocationResponse);
        lineResponse.setPoLineLocationResponses(lineLocations);
        transformPoDistribution(lineRequest, poLineLocationResponse);
    }

    private String getNeedByDate(Date dueDate) {
        return LocalDate.ofInstant(
                dueDate.toInstant(), ZoneId.systemDefault()).isAfter(LocalDate.now())
                ? dateConvertor(dueDate)
                : dateConvertor(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

    }

    private void transformPoLine(SupplyChainPORequest requestHeader, POHeaderResponse headerresponse) {
        List<POLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;
        for (PoLines poLine : requestHeader.getPoLines()) {
            lineNum = lineNum + 1;
            POLineResponse poLineResponse = new POLineResponse();
            poLineResponse.setInterfaceHeaderKey(headerresponse.getInterfaceHeaderKey());
            poLineResponse.setInterfaceLineKey(getRandomNumber());
            poLineResponse.setAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_ACTION.name()));
            poLineResponse.setLine(String.valueOf(lineNum));
            poLineResponse.setLineType(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_TYPE.name()));

            poLineResponse.setItemDescription(poLine.getItemDesc());
            poLineResponse.setCategoryName(referenceDataService.getLookupByCode(LookupEnum.SC_PO_LINE_CATEGORY.name()));//CategoryName
            String quantity = poLine.getOnOrder().toString();
            String uom = "CS";
            if (("LBS".equalsIgnoreCase(poLine.getItemSize()) ||
                    "LB AVG".equalsIgnoreCase(poLine.getItemSize())) &&
                    poLine.getListCost() < 7) {
                quantity = String.valueOf(poLine.getOnOrder() * Integer.parseInt(poLine.getItemPack()));
                uom = "LBS";
            }
            poLineResponse.setQuantity(quantity);
            poLineResponse.setUom(uom);
            poLineResponse.setPrice(String.valueOf(poLine.getListCost()));
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
            poLineResponse.setAttribute10(poLine.getItemPack());
            poLineResponse.setAttribute11(poLine.getAmtFreightBill());
            poLineResponse.setAttribute12(poLine.getAmtBackhaul());
            poLineResponse.setAttribute13(String.valueOf(poLine.getLastCost()));
            poLineResponses.add(poLineResponse);
            transformPoLineLocation(poLine, poLineResponse);
        }
        headerresponse.setPoLineResponses(poLineResponses);

    }

    private void transformPoHeader(SupplyChainPORequest poHeader, String batchId, POHeaderResponse poHeaderResponse) {
        poHeaderResponse.setInterfaceHeaderKey(getRandomNumber());
        poHeaderResponse.setAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_ACTION.name()));
        poHeaderResponse.setBatchID(batchId);

        poHeaderResponse.setImportSource(referenceDataService.getLookupByCode(LookupEnum.SC_PO_IMPORT_SOURCE.name()));
        poHeaderResponse.setApprovalAction(referenceDataService.getLookupByCode(LookupEnum.SC_PO_APPROVAL_ACTION.name()));
        poHeaderResponse.setDocumentTypeCode(referenceDataService.getLookupByCode(LookupEnum.SC_PO_DOC_TYPE_CODE.name()));
        poHeaderResponse.setStyle("");//TODO TBD no info in sheet
        poHeaderResponse.setProcurementBU(referenceDataService.getLookupByCode(LookupEnum.SC_PO_PRC_BU.name()));
        poHeaderResponse.setRequisitioningBU(referenceDataService.getLookupByCode(LookupEnum.SC_PO_REQ_BU.name()));
        poHeaderResponse.setSoldToLegalEntity(referenceDataService.getLookupByCode(LookupEnum.SC_PO_SOLD_TO_LEGAL_ENTITY.name()));
        poHeaderResponse.setBillToBU(referenceDataService.getLookupByCode(LookupEnum.SC_PO_BILL_BU.name()));//billtoBu
        poHeaderResponse.setBuyer(referenceDataService.getLookupByCode(poHeader.getBuyer()));//TODO check logic Buyer need to map scane looked (pending)
        poHeaderResponse.setCurrencyCode(referenceDataService.getLookupByCode(LookupEnum.SC_PO_CURRENCY_CODE.name()));//CurrencyCode
        poHeaderResponse.setBillToLocation(referenceDataService.getLookupByCode(LookupEnum.SC_PO_BILL_TO_LOC.name()));//BillToLocation

        poHeaderResponse.setShipToLocation("");//TODO check logic no lookup available similar to buyer
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(poHeader.getVendorNbr());
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            poHeaderResponse.setSupplier(supplier.getSupplierName());
            poHeaderResponse.setSupplierNumber(supplier.getSupplierNumber());
            poHeaderResponse.setSupplierSite(supplier.getSupplierSiteCode());
        }
        String paymentTerms = getPaymentTerms(poHeader);
        poHeaderResponse.setPaymentTerms(paymentTerms);
        poHeaderResponse.setRequiredAcknowledgment(referenceDataService.getLookupByCode(LookupEnum.SC_PO_REQU_ACK.name()));
        poHeaderResponse.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.SC_PO_ATTRIBUTE_CAT.name()));
        poHeaderResponse.setAttribute1(poHeader.getBuyer());
        poHeaderResponse.setAttribute2(poHeader.getBuyerName());
        poHeaderResponse.setAttribute3(poHeader.getPoNbr());
        poHeaderResponse.setAttribute4(poHeader.getVendorFac());
        poHeaderResponse.setAttribute5(poHeader.getVendFacName());
        poHeaderResponse.setAttribute6(poHeader.getVendorNbr());
        poHeaderResponse.setAttribute7(intToStringConvertor(poHeader.getOrderedQty()));
        poHeaderResponse.setAttribute8(intToStringConvertor(poHeader.getOrderedWeight()));
        poHeaderResponse.setAttribute9(poHeader.getFreightAllow());
        poHeaderResponse.setAttribute13(poHeader.getBackhaul());
        poHeaderResponse.setAttribute14(poHeader.getFlagBackhaul());
        poHeaderResponse.setAttribute15(poHeader.getFreightBillFlag());
        poHeaderResponse.setAttributeDate1(dateConvertor(poHeader.getDateOrdered()));
        poHeaderResponse.setAttributeDate2(dateConvertor(poHeader.getDatePickup()));
        poHeaderResponse.setAttributeDate3(dateConvertor(poHeader.getDateReceived()));
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
