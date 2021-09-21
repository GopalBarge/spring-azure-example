package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.supplychain.PoLines;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainReceiptRequest;
import com.sal.prompt.web.dto.request.supplychain.SupplychainReceipt;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.LookupEnum;
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
    String RECEIPT_HEADER_FILE_NAME = "RECEIPT_HEADERS_INTERFACE.csv";
    String RECEIPT_LINE_FILE_NAME = "RECEIPT_LINES_INTERFACE.csv";


    public SupplyChainReceiptProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
        super(fbdiFormatService);
        this.fbdiFormatService = fbdiFormatService;
        this.referenceDataService = referenceDataService;
    }

    @Override
    String getSourceSystem() {
        return "Receipt";
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
        Map<String, List> fileDateMap = new HashMap<>(2);
        List<ReceiptHeaderResponse> transformedData = data.stream().map(d -> (ReceiptHeaderResponse) d).collect(Collectors.toList());
        List<ReceiptLineResponse> poLineResponses = transformedData.stream().flatMap(e -> e.getReceiptLineResponses().stream()).sorted(Comparator.comparing(ReceiptLineResponse::getHeaderInterfaceNumber)).collect(Collectors.toList());

        fileDateMap.put(RECEIPT_HEADER_FILE_NAME, transformedData);
        fileDateMap.put(RECEIPT_LINE_FILE_NAME, poLineResponses);
        return fileDateMap;
    }

    @Override
    protected TargetSystemResponse transform(SourceSystemRequest request,String batchId) {
        POHeaderResponse poHeaderResponse = new POHeaderResponse();
        if (request instanceof SupplychainReceipt) {
            SupplychainReceipt input = (SupplychainReceipt) request;
//            transformPoHeader(input, poHeaderResponse);
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
        poLineLocationResponse1.setShipToOrganization(referenceDataService.getLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_SHIP_TO_ORG.name()));
//            poLineLocation1.setQuantity();
//            poLineLocation1.setNeedByDate();
        poLineLocationResponse1.setDestinationTypeCode(referenceDataService.getLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_DEST_TYPE_CODE.name()));
        poLineLocationResponse1.setAccrueAtReceipt(referenceDataService.getLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_LOC_ACCR_AT_RECPT.name()));
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
            poLineResponse.setCategoryName(referenceDataService.getLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_CATEGORY.name()));//CategoryName
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
            poLineResponse.setAttributeCategory(referenceDataService.getLookupByCode(SupplyChainPOLineLookupEnum.SC_PO_LINE_ATTRIBUTE_CAT.name()));//AttributeCategory
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

    private void transformPoHeader(SupplyChainReceiptRequest request, ReceiptHeaderResponse headerResponse) {
        headerResponse.setHeaderInterfaceNumber(getRandomNumber());
        headerResponse.setReceiptSourceCode(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_SOURCE_CODE.name()));
        headerResponse.setASNType(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_ASN_TYPE.name()));
        headerResponse.setTransactionType(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_TXN_TYPE.name()));
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(request.getVendorNbr());
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            headerResponse.setSupplierName(supplier.getSupplierName());
            headerResponse.setSupplierNumber(supplier.getSupplierNumber());
            headerResponse.setSupplierSiteCode(supplier.getSupplierSiteCode());
        }

        headerResponse.setEmployeeName(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_EMP_NAME.name()));
        headerResponse.setTransactionDate(dateConvertor(request.getRcvDate()));//Pass SYSDate
        headerResponse.setBusinessUnit(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_BU.name()));
        headerResponse.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_ATTRIBUTE_CAT.name()));
//        headerResponse.setAttribute1(request.getReceiptId());
//        headerResponse.setAttribute1(request.getStoreId());
//        headerResponse.setAttribute1(request.getUpcCode());
//        headerResponse.setAttribute1(request.getRetailDepartment());
//        headerResponse.setAttribute1(request.getStoreId()+request.getSupplierInvoiceNo());
//        headerResponse.setAttribute1(request.getCreatedByUserid());
//        headerResponse.setAttribute1(request.getReceiptItemId());
//        headerResponse.setAttribute1(request.getDescription());
        //headerResponse.setAttribute1(dsdReceiptRequest.getCreatedTs);//TODO CreatedTs not available check with TEAM
    }
    private String dateConvertor(Date value) {
        SimpleDateFormat dateformater = new SimpleDateFormat("yyyy/MM/dd");
        String result = dateformater.format(value);
        return result;
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
