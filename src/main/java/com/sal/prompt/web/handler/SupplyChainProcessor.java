package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.AS400Request;
import com.sal.prompt.web.dto.POHeader;
import com.sal.prompt.web.dto.PoLines;
import com.sal.prompt.web.dto.response.PODistributionResponse;
import com.sal.prompt.web.dto.response.POHeaderResponse;
import com.sal.prompt.web.dto.response.POLineResponse;
import com.sal.prompt.web.dto.response.POLineLocationResponse;
import com.sal.prompt.web.model.POLookupEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class SupplyChainProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;

    public SupplyChainProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
        super(fbdiFormatService);
        this.fbdiFormatService = fbdiFormatService;
        this.referenceDataService = referenceDataService;
    }

    private String getRandomNumber() {
        return String.valueOf(new Random().nextInt(1000000));
    }

    @Override
    POHeaderResponse transform(AS400Request input) {
        POHeaderResponse poHeaderResponse = new POHeaderResponse();
        transformPoHeader(input.getPOHeader(), poHeaderResponse);
        transformPoLine(input.getPOHeader(), poHeaderResponse);
        transformPoLineLocation(input.getPOHeader(), poHeaderResponse);
        transformPoDistribution(input.getPOHeader(), poHeaderResponse);
        return poHeaderResponse;
    }

    private void transformPoDistribution(POHeader poHeader, POHeaderResponse poHeaderResponse) {
        for (POLineResponse poLineResponse : poHeaderResponse.getPoLineResponses()) {
            for (POLineLocationResponse poLineLocationResponse : poLineResponse.getPoLineLocationResponses()) {
                List<PODistributionResponse> poDistributionResponses = new ArrayList<>();

                PODistributionResponse poDistributionResponse1 = new PODistributionResponse();
                PODistributionResponse poDistributionResponse2 = new PODistributionResponse();

                poDistributionResponse1.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
                poDistributionResponse1.setInterfaceDistributionKey(getRandomNumber());
                poDistributionResponse1.setDistribution(poLineLocationResponse.getInterfaceLineLocationKey());
                poDistributionResponse1.setRequester(referenceDataService.getPOLookupByCode(POLookupEnum.Requester));
//                poDistribution1.setQuantity();
                poDistributionResponse1.setChargeAccountSegment1(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment1));
                poDistributionResponse1.setChargeAccountSegment2(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment2));
                poDistributionResponse1.setChargeAccountSegment3(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment3));
                poDistributionResponse1.setChargeAccountSegment4(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment4));
                poDistributionResponse1.setChargeAccountSegment5(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment5));
                poDistributionResponse1.setChargeAccountSegment6(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment6));
                poDistributionResponse1.setChargeAccountSegment7(referenceDataService.getPOLookupByCode(POLookupEnum.ChargeAccountSegment7));


                poDistributionResponse2.setInterfaceDistributionKey(getRandomNumber());
                poDistributionResponse2.setInterfaceLineLocationKey(poLineLocationResponse.getInterfaceLineLocationKey());
                poDistributionResponses.add(poDistributionResponse1);
                poDistributionResponses.add(poDistributionResponse2);
                poLineLocationResponse.setPoDistributionResponses(poDistributionResponses);
            }
        }
    }

    private void transformPoLineLocation(POHeader poHeader, POHeaderResponse poHeaderResponse) {


        for (POLineResponse poLineResponse : poHeaderResponse.getPoLineResponses()) {
            List<POLineLocationResponse> lineLocations = new ArrayList<>();
            POLineLocationResponse poLineLocationResponse1 = new POLineLocationResponse();
            poLineLocationResponse1.setInterfaceLineKey(poLineResponse.getInterfaceLineKey());
            poLineLocationResponse1.setInterfaceLineLocationKey(getRandomNumber());
            poLineLocationResponse1.setSchedule(poLineResponse.getInterfaceLineKey());
            poLineLocationResponse1.setShipToLocation(referenceDataService.getPOLookupByCode(POLookupEnum.ShipToLocation));
            poLineLocationResponse1.setShipToOrganization(referenceDataService.getPOLookupByCode(POLookupEnum.ShipToOrganization));
//            poLineLocation1.setQuantity();
//            poLineLocation1.setNeedByDate();
            poLineLocationResponse1.setDestinationTypeCode(referenceDataService.getPOLookupByCode(POLookupEnum.DestinationTypeCode));
            poLineLocationResponse1.setAccrueAtReceipt(referenceDataService.getPOLookupByCode(POLookupEnum.AccrueAtReceipt));
//            poLineLocation1.setRequestedDeliveryDate();


            POLineLocationResponse poLineLocationResponse2 = new POLineLocationResponse();
            poLineLocationResponse2.setInterfaceLineKey(poLineResponse.getInterfaceLineKey());
            poLineLocationResponse2.setInterfaceLineLocationKey(getRandomNumber());

            lineLocations.add(poLineLocationResponse1);
            lineLocations.add(poLineLocationResponse2);
            poLineResponse.setPoLineLocationResponses(lineLocations);
        }
    }

    private void transformPoLine(POHeader poHeader, POHeaderResponse poHeaderResponse) {
        List<POLineResponse> poLineResponses = new ArrayList<>();
        for (PoLines poLine : poHeader.getPoLines()) {
            POLineResponse poLineResponse = new POLineResponse();
            poLineResponse.setInterfaceHeaderKey(poHeaderResponse.getInterfaceHeaderKey());
            poLineResponse.setInterfaceLineKey(getRandomNumber());
            poLineResponse.setItemDescription(poLine.getItemDesc());
            poLineResponse.setCategoryName(referenceDataService.getPOLookupByCode(POLookupEnum.CategoryName));
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
            poLineResponse.setAttributeCategory(referenceDataService.getPOLookupByCode(POLookupEnum.AttributeCategory));
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
        }
        poHeaderResponse.setPoLineResponses(poLineResponses);

    }

    private void transformPoHeader(POHeader poHeader, POHeaderResponse poHeaderResponse) {
        poHeaderResponse.setInterfaceHeaderKey(getRandomNumber());
        poHeaderResponse.setAction(referenceDataService.getPOLookupByCode(POLookupEnum.ACTION));
        poHeaderResponse.setImportSource(referenceDataService.getPOLookupByCode(POLookupEnum.IMPORT_SOURCE));
        poHeaderResponse.setApprovalAction(referenceDataService.getPOLookupByCode(POLookupEnum.ApprovalAction));
        poHeaderResponse.setDocumentTypeCode(referenceDataService.getPOLookupByCode(POLookupEnum.DocumentTypeCode));
        poHeaderResponse.setProcurementBU(referenceDataService.getPOLookupByCode(POLookupEnum.ProcurementBU));
        poHeaderResponse.setRequisitioningBU(referenceDataService.getPOLookupByCode(POLookupEnum.RequisitioningBU));
        poHeaderResponse.setSoldToLegalEntity(referenceDataService.getPOLookupByCode(POLookupEnum.SoldToLegalEntity));
        poHeaderResponse.setBillToBU(referenceDataService.getPOLookupByCode(POLookupEnum.BillToBU));
        poHeaderResponse.setBuyer(referenceDataService.getPOLookupByCode(POLookupEnum.Buyer));
        poHeaderResponse.setCurrencyCode(referenceDataService.getPOLookupByCode(POLookupEnum.CurrencyCode));
        poHeaderResponse.setBillToLocation(referenceDataService.getPOLookupByCode(POLookupEnum.BillToLocation));
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(poHeader.getVendorNbr());
        if(supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            poHeaderResponse.setSupplier(supplier.getSupplierName());
            poHeaderResponse.setSupplierNumber(supplier.getSupplierNumber());
            poHeaderResponse.setSupplierSite(supplier.getSupplierSiteCode());
        }
        String paymentTerms = getPaymentTerms(poHeader);
        poHeaderResponse.setPaymentTerms(paymentTerms);
        poHeaderResponse.setRequiredAcknowledgment(referenceDataService.getPOLookupByCode(POLookupEnum.RequiredAcknowledgment));
        poHeaderResponse.setAttributeCategory(referenceDataService.getPOLookupByCode(POLookupEnum.AttributeCategory));
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

    private String getPaymentTerms(POHeader poHeader) {
        String paymentTerms = "";
        if (!poHeader.getTermsNetDays().equalsIgnoreCase("0")) {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + " NET " + poHeader.getTermsNetDays();
        } else {
            paymentTerms = poHeader.getTermsPercent() + "/" + poHeader.getTermsDays() + ",NET," + 30;
        }
        return paymentTerms;
    }

}
