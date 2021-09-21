package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.dsd.DSDPORequest;
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
public class DSDPOProcessor extends SourceDataProcessor {


    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;

    private final String PO_HEADER_FILE_NAME = "PoHeadersInterfaceOrder.csv";
    private final String PO_LINE_FILE_NAME = "PoLinesInterfaceOrder.csv";
    private final String PO_LINE_LOCATION_FILE_NAME = "PoLineLocationsInterfaceOrder.csv";
    private final String PO_DISTRIBUTION_FILE_NAME = "PoDistributionsInterfaceOrder.csv";

    public DSDPOProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
        super(fbdiFormatService);
        this.fbdiFormatService = fbdiFormatService;
        this.referenceDataService = referenceDataService;
    }

    @Override
    String getSourceSystem() {
        return "DSD";
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
        if (request instanceof DSDPORequest) {
            DSDPORequest input = (DSDPORequest) request;
            transformPoHeader(input, batchId, poHeaderResponse);
            transformPoLine(input, poHeaderResponse);
//            transformPoLineLocation(input.getPOHeader(), poHeaderResponse);
//            transformPoDistribution(input.getPOHeader(), poHeaderResponse);
        }
        return poHeaderResponse;
    }

    private void transformPoDistribution(DSDPORequest poLine, POLineLocationResponse poLineLocationResponse) {

        List<PODistributionResponse> poDistributionResponses = new ArrayList<>();

        PODistributionResponse poDistributionResponse = new PODistributionResponse();
        poDistributionResponse.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
        poDistributionResponse.setInterfaceDistributionKey(getRandomNumber());
        poDistributionResponse.setDistribution(poLineLocationResponse.getSchedule());//Use same Line number# which you referred while creating Line csv file
        //poDistributionResponse.setRequester(poDistributionResponse.getBu);//TODO check logic

        poDistributionResponse.setQuantity(poLineLocationResponse.getQuantity());//TODO cross check logic
        poDistributionResponse.setChargeAccountSegment1(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DISTI_SEGMENT1.name()));
        poDistributionResponse.setChargeAccountSegment2(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DISTI_SEGMENT2.name()));
        poDistributionResponse.setChargeAccountSegment3(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DISTI_SEGMENT3.name()));
        poDistributionResponse.setChargeAccountSegment4(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DISTI_SEGMENT4.name()));
        poDistributionResponse.setChargeAccountSegment5(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DISTI_SEGMENT5.name()));
        poDistributionResponse.setChargeAccountSegment6(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DISTI_SEGMENT6.name()));
        poDistributionResponse.setChargeAccountSegment7(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DISTI_SEGMENT7.name()));

        poDistributionResponses.add(poDistributionResponse);
        poLineLocationResponse.setPoDistributionResponses(poDistributionResponses);
    }

    private void transformPoLineLocation(DSDPORequest lineRequest, POLineResponse lineResponse) {

        List<POLineLocationResponse> lineLocations = new ArrayList<>();
        POLineLocationResponse poLineLocationResponse = new POLineLocationResponse();
        poLineLocationResponse.setInterfaceLineKey(lineResponse.getInterfaceLineKey());
        poLineLocationResponse.setInterfaceLineLocationKey(getRandomNumber());
        poLineLocationResponse.setSchedule(lineResponse.getLine()); //Use same Line number# which you referred while creating Line csv file
//        poLineLocationResponse1.setShipToLocation(referenceDataService.getPOLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_SHIP_TO_LOC.name()));//TODO ShipToLocation
        poLineLocationResponse.setShipToOrganization(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_LOC_SHIP_TO_ORG.name()));
        poLineLocationResponse.setQuantity(lineResponse.getQuantity());
        //poLineLocationResponse.setNeedByDate(getNeedByDate(lineRequest.getSupplierInvoiceDate())); //TODO Check logic
        poLineLocationResponse.setDestinationTypeCode(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_LOC_DEST_TYPE_CODE.name()));
        poLineLocationResponse.setAccrueAtReceipt(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_LOC_ACCR_AT_RECPT.name()));

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

    private void transformPoLine(DSDPORequest poLine, POHeaderResponse headerresponse) {
        List<POLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;
//        for (PoLines poLine : requestHeader.getPoLines()) {
            lineNum = lineNum + 1;
            POLineResponse poLineResponse = new POLineResponse();
            poLineResponse.setInterfaceHeaderKey(headerresponse.getInterfaceHeaderKey());
            poLineResponse.setInterfaceLineKey(getRandomNumber());
            poLineResponse.setAction(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_ACTION.name()));
            poLineResponse.setLine(String.valueOf(lineNum));
            poLineResponse.setLineType(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_TYPE.name()));

            poLineResponse.setItemDescription(poLine.getDescription());
            poLineResponse.setCategoryName(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_CATEGORY.name()));//CategoryName

            String uom = "CS";
            if ("1".equalsIgnoreCase(poLine.getCasePack()))
            {
                uom = "EA";
            }

            poLineResponse.setQuantity(intToStringConvertor(poLine.getQtyReceived()));//TODO cross-check  the logic
            poLineResponse.setUom(uom);
            poLineResponse.setPrice(String.valueOf(poLine.getItemCost()));
            poLineResponse.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_ATTRIBUTE_CAT.name()));
            poLineResponse.setAttribute1(poLine.getStoreId());
            poLineResponse.setAttribute2(poLine.getSupplierInvoiceNo());
            poLineResponse.setAttribute3(String.valueOf(poLine.getReceiptItemId()));
            poLineResponse.setAttribute4(poLine.getUpcCode());
            poLineResponse.setAttribute5(String.valueOf(poLine.getRetailDepartment()));
            poLineResponse.setAttribute6(poLine.getRetailClass());
            poLineResponse.setAttribute7(poLine.getItemSize());
            poLineResponse.setAttribute8(poLine.getUomDescriptor());
            poLineResponse.setAttribute9(String.valueOf(poLine.getRetailQty()));
            poLineResponse.setAttribute10(String.valueOf(poLine.getRetailPrice()));
            poLineResponse.setAttribute11(poLine.getMerchandisingDepartment());
            poLineResponse.setAttribute12(poLine.getMerchandisingClass());
            poLineResponse.setAttribute13(poLine.getMerchandisingSubClass());
            poLineResponse.setAttribute14(poLine.getSubclassMerchandiser());
            poLineResponse.setAttributeDate1(dateConvertor(poLine.getCreatedTs()));
            poLineResponse.setAttributeDate2(poLine.getSupplierInvoiceDate());
            poLineResponses.add(poLineResponse);
            transformPoLineLocation(poLine, poLineResponse);
//        }
        headerresponse.setPoLineResponses(poLineResponses);

    }

    private void transformPoHeader(DSDPORequest poHeader, String batchId, POHeaderResponse poHeaderResponse) {
        poHeaderResponse.setInterfaceHeaderKey(getRandomNumber());
        poHeaderResponse.setAction(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_ACTION.name()));
        poHeaderResponse.setBatchID(batchId);
        poHeaderResponse.setDocumentTypeCode(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DOC_TYPE_CODE.name()));
        poHeaderResponse.setProcurementBU(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_PRC_BU.name()));
        poHeaderResponse.setRequisitioningBU(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_REQ_BU.name()));
        poHeaderResponse.setSoldToLegalEntity(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_SOLD_TO_LEGAL_ENTITY.name()));
        poHeaderResponse.setBillToBU(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_BILL_BU.name()));//billtoBu
        poHeaderResponse.setBuyer(referenceDataService.getLookupByCode(poHeader.getBuyer()));//TODO check logic Buyer need to map
        poHeaderResponse.setCurrencyCode(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_CURRENCY_CODE.name()));//CurrencyCode
        poHeaderResponse.setBillToLocation(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_BILL_TO_LOC.name()));//BillToLocation

        poHeaderResponse.setShipToLocation("");//TODO check logic no lookup available
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(poHeader.getVendorNo());
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            poHeaderResponse.setSupplier(supplier.getSupplierName());
            poHeaderResponse.setSupplierNumber(supplier.getSupplierNumber());
            poHeaderResponse.setSupplierSite(supplier.getSupplierSiteCode());
        }
        String paymentTerms = getPaymentTerms(poHeader);
        poHeaderResponse.setPaymentTerms(paymentTerms);
        poHeaderResponse.setRequiredAcknowledgment(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_REQU_ACK.name()));
        poHeaderResponse.setAttribute1(poHeader.getCreatedByUserid());
        poHeaderResponse.setAttribute2(poHeader.getReceiptId());
        poHeaderResponse.setAttribute3(poHeader.getStoreId());
        poHeaderResponse.setAttributeDate1(dateConvertor(poHeader.getCreatedTs()));
        poHeaderResponse.setAttributeDate2(poHeader.getSupplierInvoiceDate());
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

    private String getPaymentTerms(DSDPORequest poHeader) {
        String paymentTerms = "";
        if (!poHeader.getTermNetDays().equalsIgnoreCase("0")) {
            paymentTerms = poHeader.getTermPercent() + "/" + poHeader.getTermDays() + " ,NET, " + poHeader.getTermNetDays();
        } else {
            paymentTerms = poHeader.getTermPercent() + "/" + poHeader.getTermDays() + ",NET," + 30;
        }
        return paymentTerms;
    }

}