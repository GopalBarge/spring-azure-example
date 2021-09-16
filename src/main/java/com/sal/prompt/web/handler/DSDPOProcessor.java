package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.dsd.DSDPORequest;
import com.sal.prompt.web.dto.response.*;

import com.sal.prompt.web.model.lookup.DSDPOLookupEnum;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DSDPOProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;
    String DSD_PO_HEADER_FILE_NAME = "DSD_PO_HEADERS_INTERFACE.csv";
    String DSD_PO_LINE_FILE_NAME = "DSD_PO_LINES_INTERFACE.csv";
    String DSD_PO_LINE_LOCATION_FILE_NAME = "DSD_PO_LINE_LOCATIONS_INTERFACE.csv";
    String DSD_PO_DISTRIBUTION_FILE_NAME = "DSD_PO_DISTRIBUTIONS_INTERFACE.csv";

    public DSDPOProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
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
    protected Map<String, List> getFileDataMap(List<TargetSystemResponse> transformedData) {
        return null;
    }

    @Override
    public TargetSystemResponse transform(SourceSystemRequest request) {
        POHeaderResponse poHeaderResponse = new POHeaderResponse();
        if (request instanceof DSDPORequest) {
            DSDPORequest input = (DSDPORequest) request;
            transformPoHeader(input, poHeaderResponse);
           // transformPoLine(input.getPOHeader(), poHeaderResponse);
//            transformPoLineLocation(input.getPOHeader(), poHeaderResponse);
//            transformPoDistribution(input.getPOHeader(), poHeaderResponse);
        }
        return poHeaderResponse;
    }


    private void transformPoHeader(DSDPORequest dsdpoHeader, POHeaderResponse dsdpoHeaderResponse)
    {
        dsdpoHeaderResponse.setInterfaceHeaderKey(getRandomNumber());
        dsdpoHeaderResponse.setAction(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_ACTION.name()));
       // dsdpoHeaderResponse.setBatchID("");
        dsdpoHeaderResponse.setDocumentTypeCode(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_DOC_TYPE_CODE.name()));
        dsdpoHeaderResponse.setProcurementBU(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_PRC_BU.name()));
        dsdpoHeaderResponse.setRequisitioningBU(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_REQU_ACK.name()));
        dsdpoHeaderResponse.setSoldToLegalEntity(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_SOLD_TO_LEGAL_ENTITY.name()));
        dsdpoHeaderResponse.setBillToBU(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_BILL_BU.name()));
        //dsdpoHeaderResponse.setBuyer(referenceDataService.getPOLookupByCode(DSDPOLookupEnum));
        dsdpoHeaderResponse.setCurrencyCode(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_CURRENCY_CODE.name()));
        dsdpoHeaderResponse.setBillToLocation(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_BILL_TO_LOC.name()));
        //dsdpoHeaderResponse.setShipToLocation(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.);
        //dsdpoHeaderResponse.setSupplier(dsdpoHeader.getVendorNo());
        //dsdpoHeaderResponse.setSupplierNumber(dsdpoHeader.getVendorNo());
        //dsdpoHeaderResponse.setSupplierSite(dsdpoHeader.getVendorNo());
        //dsdpoHeaderResponse.setPaymentTerms();
        dsdpoHeaderResponse.setRequiredAcknowledgment(referenceDataService.getPOLookupByCode(DSDPOLookupEnum.DSD_PO_REQU_ACK.name()));
        //dsdpoHeaderResponse.setAttribute1();
       // dsdpoHeaderResponse.setAttribute2();
       // dsdpoHeaderResponse.setAttribute3();
       // dsdpoHeaderResponse.setAttributeDate1();
      //  dsdpoHeaderResponse.setAttributeDate2();

    }


}