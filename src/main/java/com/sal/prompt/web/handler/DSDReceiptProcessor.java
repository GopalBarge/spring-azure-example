package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.dsd.DSDReceiptRequest;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.LookupEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DSDReceiptProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;
    String DSD_PO_HEADER_FILE_NAME = "DSD_PO_HEADERS_INTERFACE.csv";
    String DSD_PO_LINE_FILE_NAME = "DSD_PO_LINES_INTERFACE.csv";

    public DSDReceiptProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
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
    protected Map<String, List> getFileDataMap(List<TargetSystemResponse> transformedData) {
        return null;
    }

    @Override
    public TargetSystemResponse transform(SourceSystemRequest request,String batchId) {
        ReceiptHeaderResponse poHeaderResponse = new ReceiptHeaderResponse();
        if (request instanceof DSDReceiptRequest) {
            DSDReceiptRequest input = (DSDReceiptRequest) request;
            transformPoHeader(input , poHeaderResponse);
            transformPoLine(input , poHeaderResponse);
        }
        return poHeaderResponse;
    }
    private void transformPoLine(DSDReceiptRequest poLine, ReceiptHeaderResponse headerresponse) {
        List<ReceiptLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;
//        for (PoLines poLine : requestHeader.getPoLines()) {
        lineNum = lineNum + 1;
        ReceiptLineResponse poLineResponse = new ReceiptLineResponse();
        poLineResponse.setInterfaceLineNumber(getRandomNumber());
        poLineResponse.setTransactionType(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_TXN_TYPE.name()));
        poLineResponse.setAutoTransactCode(getDate());//Pass SYSDate
        poLineResponse.setSourceDocumentCode(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_SOURCE_DOC_CODE.name()));
        poLineResponse.setReceiptSourceCode(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_SOURCE_CODE.name()));//CategoryName
        poLineResponse.setHeaderInterfaceNumber(headerresponse.getHeaderInterfaceNumber());
        poLineResponse.setItemDescription(poLine.getDescription());
        //poLineResponse.setDocumentNumber(); //TODO
       // poLineResponse.setDocumentLineNumber();//TODO
        poLineResponse.setDocumentScheduleNumber(String.valueOf(lineNum));
        poLineResponse.setDocumentDistributionNumber(String.valueOf(lineNum));
        poLineResponse.setBusinessUnit(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_BU.name()));
        String quantity = String.valueOf(Integer.parseInt(poLine.getCasePack()) * (poLine.getQtyReceived()));//TODO item pack is string it should be integer
        poLineResponse.setQuantity(quantity);
        String uom = "CS";
        if ("1".equalsIgnoreCase(poLine.getCasePack()))
        {
            uom = "EA";
        }
        poLineResponse.setUom(uom);//TODO check logic
        poLineResponse.setEmployeeName(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_EMP_NAME.name()));
        poLineResponses.add(poLineResponse);

//        }
//        headerresponse.setReciptLineResponses(poLineResponses);

    }

    private void transformPoHeader(DSDReceiptRequest dsdReceiptRequest, ReceiptHeaderResponse dsdRecieptHeaderResponse)
    {
        dsdRecieptHeaderResponse.setHeaderInterfaceNumber(getRandomNumber());
        dsdRecieptHeaderResponse.setReceiptSourceCode(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_SOURCE_CODE.name()));
        dsdRecieptHeaderResponse.setASNType(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_ASN_TYPE.name()));
        dsdRecieptHeaderResponse.setTransactionType(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_TXN_TYPE.name()));
        Optional<Supplier> supplierOpt = referenceDataService.getPOSupplierByCode(dsdReceiptRequest.getVendorNo());
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            dsdRecieptHeaderResponse.setSupplierName(supplier.getSupplierName());
            dsdRecieptHeaderResponse.setSupplierSiteCode(supplier.getSupplierSiteCode());
        }

        dsdRecieptHeaderResponse.setEmployeeName(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_EMP_NAME.name()));
        dsdRecieptHeaderResponse.setTransactionDate(getDate());//Pass SYSDate
        dsdRecieptHeaderResponse.setBusinessUnit(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_BU.name()));
        dsdRecieptHeaderResponse.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_ATTRIBUTE_CAT.name()));
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getReceiptId());
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getStoreId());
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getUpcCode());
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getRetailDepartment());
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getStoreId()+dsdReceiptRequest.getSupplierInvoiceNo());
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getCreatedByUserid());
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getReceiptItemId());
        dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getDescription());
        //dsdRecieptHeaderResponse.setAttribute1(dsdReceiptRequest.getCreatedTs);//TODO CreatedTs not available check with TEAM
    }

    private String getDate()
    {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();
    return formatter.format(date);
    }
}