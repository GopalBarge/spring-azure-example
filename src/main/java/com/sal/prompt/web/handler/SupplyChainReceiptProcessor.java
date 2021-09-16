package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.supplychain.PoLines;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.model.lookup.SupplyChainPOLineLookupEnum;
import com.sal.prompt.web.model.lookup.SupplyChainPOLookupEnum;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SupplyChainReceiptProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;
    String PO_HEADER_FILE_NAME = "PO_HEADERS_INTERFACE.csv";
    String PO_LINE_FILE_NAME = "PO_LINES_INTERFACE.csv";
    String PO_LINE_LOCATION_FILE_NAME = "PO_LINE_LOCATIONS_INTERFACE.csv";
    String PO_DISTRIBUTION_FILE_NAME = "PO_DISTRIBUTIONS_INTERFACE.csv";

    public SupplyChainReceiptProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
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
    public TargetSystemResponse transform(SourceSystemRequest request) {
        POHeaderResponse poHeaderResponse = new POHeaderResponse();
        if (request instanceof SupplyChainReceiptProcessor) {
            SupplyChainReceiptProcessor input = (SupplyChainReceiptProcessor) request;
//            transformPoHeader(input.getPOHeader(), poHeaderResponse);
//            transformPoLine(input.getPOHeader(), poHeaderResponse);
        }
        return poHeaderResponse;
    }

    private void transformPoDistribution(PoLines poHeader, POLineLocationResponse poLineLocationResponse) {

        List<PODistributionResponse> poDistributionResponses = new ArrayList<>();

        PODistributionResponse poDistributionResponse1 = new PODistributionResponse();
        PODistributionResponse poDistributionResponse2 = new PODistributionResponse();

        poDistributionResponse1.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
        poDistributionResponse1.setInterfaceDistributionKey(getRandomNumber());
//                poDistributionResponse1.setDistribution(poLineLocationResponse.getInterfaceLineLocationKey());
//        poDistributionResponse1.setRequester(referenceDataService.getPOLookupByCode(POLookupEnum.Requester));
//                poDistribution1.setQuantity();
//        poDistributionResponse1.setChargeAccountSegment1(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment1));
//        poDistributionResponse1.setChargeAccountSegment2(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment2));
//        poDistributionResponse1.setChargeAccountSegment3(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment3));
//        poDistributionResponse1.setChargeAccountSegment4(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment4));
//        poDistributionResponse1.setChargeAccountSegment5(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment5));
//        poDistributionResponse1.setChargeAccountSegment6(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment6));
//        poDistributionResponse1.setChargeAccountSegment7(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment7));


       // poDistributionResponse2.setInterfaceDistributionKey(getRandomNumber());
      //  poDistributionResponse2.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
        poDistributionResponses.add(poDistributionResponse1);
       // poDistributionResponses.add(poDistributionResponse2);
        poLineLocationResponse.setPoDistributionResponses(poDistributionResponses);


    }

    private void transformPoLineLocation(PoLines poLine, POLineResponse poLineResponse) {

        List<POLineLocationResponse> lineLocations = new ArrayList<>();
        POLineLocationResponse poLineLocationResponse1 = new POLineLocationResponse();
        poLineLocationResponse1.setInterfaceLineKey(poLineResponse.getInterfaceLineKey());
        poLineLocationResponse1.setInterfaceLineLocationKey(getRandomNumber());
//            poLineLocationResponse1.setSchedule(poLineResponse.getInterfaceLineKey());
//        poLineLocationResponse1.setShipToLocation(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_SHIP_TO_LOC.name()));//TODO ShipToLocation
        poLineLocationResponse1.setShipToOrganization(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_SHIP_TO_ORG.name()));
//            poLineLocation1.setQuantity();
//            poLineLocation1.setNeedByDate();
        poLineLocationResponse1.setDestinationTypeCode(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_DEST_TYPE_CODE.name()));
        poLineLocationResponse1.setAccrueAtReceipt(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_ACCR_AT_RECPT.name()));
//            poLineLocation1.setRequestedDeliveryDate();

        lineLocations.add(poLineLocationResponse1);
        poLineResponse.setPoLineLocationResponses(lineLocations);

        transformPoDistribution(poLine, poLineLocationResponse1);
    }

    private void transformPoLine(SupplyChainPORequest poHeader, POHeaderResponse poHeaderResponse) {
        List<POLineResponse> poLineResponses = new ArrayList<>();
        for (PoLines poLine : poHeader.getPoLines()) {
            POLineResponse poLineResponse = new POLineResponse();
            poLineResponse.setInterfaceHeaderKey(poHeaderResponse.getInterfaceHeaderKey());
            poLineResponse.setInterfaceLineKey(getRandomNumber());
            poLineResponse.setItemDescription(poLine.getItemDesc());
            poLineResponse.setCategoryName(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_CATEGORY.name()));//CategoryName
            String quantity = "ON ORDER";
            String uom = "CS";
            if (("LBS".equalsIgnoreCase(poLine.getItemSize()) ||
                    "LB AVG".equalsIgnoreCase(poLine.getItemSize())) &&
                    poLine.getListCost() < 7) {
                quantity = String.valueOf(poLine.getOnOrder() * Integer.parseInt(poLine.getItemPack()));//TODO item pack is string it should be integer
                uom = "LBS";
            }
            poLineResponse.setQuantity(quantity);
            poLineResponse.setUom(uom);
            poLineResponse.setPrice(String.valueOf(poLine.getListCost())); //TODO item pack is string it should be integer
            poLineResponse.setAttributeCategory(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_ATTRIBUTE_CAT.name()));//AttributeCategory
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
        poHeaderResponse.setPoLineResponses(poLineResponses);

    }

    private void transformPoHeader(SupplyChainPORequest poHeader, POHeaderResponse poHeaderResponse) {
        poHeaderResponse.setInterfaceHeaderKey(getRandomNumber());
        poHeaderResponse.setAction(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_ACTION.name()));
        poHeaderResponse.setImportSource(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_IMPORT_SOURCE.name()));
        poHeaderResponse.setApprovalAction(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_APPROVAL_ACTION.name()));
        poHeaderResponse.setDocumentTypeCode(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_DOC_TYPE_CODE.name()));
        poHeaderResponse.setProcurementBU(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_PRC_BU.name()));
        poHeaderResponse.setRequisitioningBU(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_REQ_BU.name()));
        poHeaderResponse.setSoldToLegalEntity(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_SOLD_TO_LEGAL_ENTITY.name()));
        poHeaderResponse.setBillToBU(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_BILL_BU.name()));//billtoBu
        poHeaderResponse.setBuyer(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_DEF_BUYER.name()));//Buyer
        poHeaderResponse.setCurrencyCode(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_CURRENCY_CODE.name()));//CurrencyCode
        poHeaderResponse.setBillToLocation(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_BILL_TO_LOC.name()));//BillToLocation
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(poHeader.getVendorNbr());
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            poHeaderResponse.setSupplier(supplier.getSupplierName());
            poHeaderResponse.setSupplierNumber(supplier.getSupplierNumber());
            poHeaderResponse.setSupplierSite(supplier.getSupplierSiteCode());
        }
        String paymentTerms = getPaymentTerms(poHeader);
        poHeaderResponse.setPaymentTerms(paymentTerms);
        poHeaderResponse.setRequiredAcknowledgment(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_REQU_ACK.name()));
        poHeaderResponse.setAttributeCategory(referenceDataService.getPOLookupByCode(SupplyChainPOLookupEnum.SC_PO_ATTRIBUTE_CAT.name()));
        poHeaderResponse.setAttribute1(poHeader.getBuyer());
        poHeaderResponse.setAttribute2(poHeader.getBuyerName());
        poHeaderResponse.setAttribute3(poHeader.getPoNbr());
        poHeaderResponse.setAttribute4(poHeader.getVendorFac());
        poHeaderResponse.setAttribute5(poHeader.getVendFacName());
        poHeaderResponse.setAttribute6(poHeader.getVendorNbr());
        poHeaderResponse.setAttribute7(Integer.toString(poHeader.getOrderedQty()));
        poHeaderResponse.setAttribute8(Integer.toString(poHeader.getOrderedWeight()));
        poHeaderResponse.setAttribute9(poHeader.getFreightAllow());
        poHeaderResponse.setAttribute13(poHeader.getBackhaul());
        poHeaderResponse.setAttribute14(poHeader.getFlagBackhaul());
        poHeaderResponse.setAttribute15(poHeader.getFreightBillFlag());
        poHeaderResponse.setAttributeDate1(SimpleDateFormat.getDateInstance().format(poHeader.getDateOrdered()));
        poHeaderResponse.setAttributeDate2(SimpleDateFormat.getDateInstance().format(poHeader.getDatePickup()));
        poHeaderResponse.setAttributeDate3(SimpleDateFormat.getDateInstance().format(poHeader.getDateReceived()));
    }

    private String getPaymentTerms(SupplyChainPORequest poHeader) {
        String paymentTerms = "";
        if (!poHeader.getTermsNetDays().equalsIgnoreCase("0")) {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + " NET " + poHeader.getTermsNetDays();
        } else {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + ",NET," + 30;
        }
        return paymentTerms;
    }

}
