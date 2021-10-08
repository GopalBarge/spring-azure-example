package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.dsd.DSDPORequest;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.LookupEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import com.sal.prompt.web.utils.InterfaceEnum;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
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
        return InterfaceEnum.DSD_PO.name();
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
        if (request instanceof DSDPORequest) {
            DSDPORequest input = (DSDPORequest) request;
            transformPoHeader(input, batchId, poHeaderResponse);
            transformPoLine(input, poHeaderResponse);
        }
        return poHeaderResponse;
    }

    private void transformPoDistribution(DSDPORequest poLine, POLineLocationResponse poLineLocationResponse, POHeaderResponse response) {

        List<PODistributionResponse> poDistributionResponses = new ArrayList<>();

        PODistributionResponse poDistributionResponse = new PODistributionResponse();
        poDistributionResponse.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
        poDistributionResponse.setInterfaceDistributionKey(getRandomNumber());
        poDistributionResponse.setDistribution("1");//Use same Line number# which you referred while creating Line csv file
        poDistributionResponse.setRequester(response.getBuyer());

        poDistributionResponse.setQuantity(poLineLocationResponse.getQuantity());
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

    private void transformPoLineLocation(DSDPORequest lineRequest, POLineResponse lineResponse, POHeaderResponse response) {

        List<POLineLocationResponse> lineLocations = new ArrayList<>();
        POLineLocationResponse poLineLocationResponse = new POLineLocationResponse();
        poLineLocationResponse.setInterfaceLineKey(lineResponse.getInterfaceLineKey());
        poLineLocationResponse.setInterfaceLineLocationKey(getRandomNumber());
        poLineLocationResponse.setSchedule("1"); //Use same Line number# which you referred while creating Line csv file
        String shipToLocation = referenceDataService.getShipToLocation(lineRequest.getStoreId(), LookupEnum.DSD_PO_DEF_SHIP_TO_LOC_CODE);
        poLineLocationResponse.setShipToLocation(shipToLocation);
        poLineLocationResponse.setShipToOrganization(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_LOC_SHIP_TO_ORG.name()));
        poLineLocationResponse.setQuantity(lineResponse.getQuantity());
        poLineLocationResponse.setNeedByDate(getNeedByDate(lineRequest.getSupplierInvoiceDate()));
        poLineLocationResponse.setDestinationTypeCode(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_LOC_DEST_TYPE_CODE.name()));
        poLineLocationResponse.setAccrueAtReceipt(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_LOC_ACCR_AT_RECPT.name()));

        lineLocations.add(poLineLocationResponse);
        lineResponse.setPoLineLocationResponses(lineLocations);
        transformPoDistribution(lineRequest, poLineLocationResponse,response);
    }

    private String getNeedByDate(Date dueDate) {
        return LocalDate.ofInstant(
                dueDate.toInstant(), ZoneId.systemDefault()).isAfter(LocalDate.now())
                ? dateConvertor(dueDate)
                : dateConvertor(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

    }

    private void transformPoLine(DSDPORequest request, POHeaderResponse response) {
        List<POLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;
        lineNum = lineNum + 1;
        POLineResponse poLineResponse = new POLineResponse();
        poLineResponse.setInterfaceHeaderKey(response.getInterfaceHeaderKey());
        poLineResponse.setInterfaceLineKey(getRandomNumber());
        poLineResponse.setAction(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_ACTION.name()));
        poLineResponse.setLine(String.valueOf(lineNum));
        poLineResponse.setLineType(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_TYPE.name()));

        poLineResponse.setItemDescription(request.getDescription());
        poLineResponse.setCategoryName(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_CATEGORY.name()));

        String uom = "CS";
        Integer quantity = request.getCasePack();
        Integer price = request.getCasePack() * request.getItemCost();
        if (1 == request.getCasePack()) {
            quantity = request.getQtyReceived();
            uom = "EA";
            price = request.getItemCost();
        }

        poLineResponse.setQuantity(String.valueOf(quantity));
        poLineResponse.setUom(uom);
        poLineResponse.setPrice(String.valueOf(price));
        poLineResponse.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_LINE_ATTRIBUTE_CAT.name()));
        poLineResponse.setAttribute1(request.getStoreId());
        poLineResponse.setAttribute2(request.getStoreName());
        poLineResponse.setAttribute3(request.getSupplierInvoiceNo()+"_"+request.getStoreId());
        poLineResponse.setAttribute4(request.getReceiptItemId());
        poLineResponse.setAttribute5(request.getUpcCode());
        poLineResponse.setAttribute6(String.valueOf(request.getRetailDepartment()));

        poLineResponse.setAttribute7(request.getRetailClass());
        poLineResponse.setAttribute8(request.getItemSize());
        poLineResponse.setAttribute9(request.getUomDescriptor());
        poLineResponse.setAttribute10(String.valueOf(request.getRetailQty()));
        poLineResponse.setAttribute11(String.valueOf(request.getRetailPrice()));
        poLineResponse.setAttribute12(request.getMerchandisingDepartment());
        poLineResponse.setAttribute13(request.getMerchandisingClass());
        poLineResponse.setAttribute14(request.getMerchandisingSubClass());
        poLineResponse.setAttribute15(request.getSubclassMerchandiser());
        poLineResponse.setAttribute16(String.valueOf(request.getItemCost()));
        poLineResponse.setAttributeDate1(dateConvertor(request.getCreatedTs()));
        poLineResponse.setAttributeDate2(dateConvertor(request.getSupplierInvoiceDate()));
        poLineResponses.add(poLineResponse);
        transformPoLineLocation(request, poLineResponse,response);
//        }
        response.setPoLineResponses(poLineResponses);

    }

    private void transformPoHeader(DSDPORequest request, String batchId, POHeaderResponse response) {
        response.setInterfaceHeaderKey(getRandomNumber());
        response.setAction(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_ACTION.name()));
        response.setBatchID(batchId);
        response.setImportSource(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_IMPORT_SOURCE.name()));
        //TODO add minsing field check mapping sheet
        response.setDocumentTypeCode(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_DOC_TYPE_CODE.name()));
        response.setProcurementBU(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_PRC_BU.name()));
        response.setRequisitioningBU(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_REQ_BU.name()));
        response.setSoldToLegalEntity(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_SOLD_TO_LEGAL_ENTITY.name()));
        response.setBillToBU(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_BILL_BU.name()));
        String buyerKey = request.getMerchandisingClass() + "-" + request.getMerchandisingSubClass() + "-" + request.getSubclassMerchandiser();
        response.setBuyer(referenceDataService.getBuyer(buyerKey, LookupEnum.DSD_PO_DEF_BUYER));
        response.setCurrencyCode(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_CURRENCY_CODE.name()));
        response.setBillToLocation(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_BILL_TO_LOC.name()));

        response.setShipToLocation(referenceDataService.getShipToLocation(request.getStoreId(), LookupEnum.DSD_PO_DEF_SHIP_TO_LOC_CODE));
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(request.getVendorNo());
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            response.setSupplier(supplier.getSupplierName());
            response.setSupplierNumber(supplier.getSupplierNumber());
            response.setSupplierSite(supplier.getSupplierSiteCode());
        }
        String paymentTerms = getPaymentTerms(request);
        response.setPaymentTerms(paymentTerms);
        response.setRequiredAcknowledgment(referenceDataService.getLookupByCode(LookupEnum.DSD_PO_REQU_ACK.name()));
        response.setAttribute1(request.getCreatedByUserid());
        response.setAttribute2(request.getReceiptId());
        response.setAttribute3(request.getStoreId());
//        response.setAttributeDate1(dateConvertor(request.getCreatedTs()));
//        response.setAttributeDate2(request.getSupplierInvoiceDate());
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