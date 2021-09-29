package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.dsd.DSDReceipt;
import com.sal.prompt.web.dto.request.dsd.DSDReceiptRequest;
import com.sal.prompt.web.dto.response.ReceiptHeaderResponse;
import com.sal.prompt.web.dto.response.ReceiptLineResponse;
import com.sal.prompt.web.dto.response.TargetSystemResponse;
import com.sal.prompt.web.model.LookupEnum;
import com.sal.prompt.web.model.OpenPO;
import com.sal.prompt.web.model.POTypesEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import com.sal.prompt.web.utils.CommonUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.sal.prompt.web.utils.CommonUtility.getDate;

@Component
public class DSDReceiptProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;
    String DSD_PO_HEADER_FILE_NAME = "HEADERS_INTERFACE.csv";
    String DSD_PO_LINE_FILE_NAME = "LINES_INTERFACE.csv";

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


    private String getRandomNumber() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Override
    protected Map<String, List> getFileDataMap(List<TargetSystemResponse> transformedData) {
        return null;
    }

    @Override
    public TargetSystemResponse transform(SourceSystemRequest request, String batchId) {
        ReceiptHeaderResponse poHeaderResponse = new ReceiptHeaderResponse();
        if (request instanceof DSDReceipt) {
            DSDReceipt input = (DSDReceipt) request;
            transformPoHeader(input, poHeaderResponse);
            transformPoLine(input, poHeaderResponse);
        }
        return poHeaderResponse;
    }

    private void transformPoLine(DSDReceipt receipt, ReceiptHeaderResponse headerresponse) {
        List<ReceiptLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;

        for (DSDReceiptRequest poLine : receipt.getLines()) {
            lineNum = lineNum + 1;
            ReceiptLineResponse poLineResponse = new ReceiptLineResponse();
            poLineResponse.setInterfaceLineNumber(getRandomNumber());
            poLineResponse.setTransactionType(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_TXN_TYPE.name()));
            poLineResponse.setAutoTransactCode(getDate());//Pass SYSDate
            poLineResponse.setSourceDocumentCode(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_SOURCE_DOC_CODE.name()));
            poLineResponse.setReceiptSourceCode(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_SOURCE_CODE.name()));//CategoryName
            poLineResponse.setHeaderInterfaceNumber(headerresponse.getHeaderInterfaceNumber());
            poLineResponse.setItemDescription(poLine.getDescription());

            List<OpenPO> openPOs = referenceDataService.getOpenPos(poLine.getReceiptId(), POTypesEnum.DSD_RECPT);
            Optional<OpenPO> openPo = openPOs.stream().filter(po -> po.getItemUpc().equalsIgnoreCase(poLine.getUpcCode())).findAny();
            if (openPo.isPresent()) {
                poLineResponse.setDocumentNumber(openPo.get().getDocumentNumber());
                poLineResponse.setDocumentLineNumber(openPo.get().getLineNum());
            }

            poLineResponse.setDocumentScheduleNumber(String.valueOf(lineNum));
            poLineResponse.setDocumentDistributionNumber(String.valueOf(lineNum));
            poLineResponse.setBusinessUnit(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_BU.name()));
            String quantity = String.valueOf(poLine.getCasePack() * poLine.getQtyReceived());
            poLineResponse.setQuantity(quantity);
            String uom = "CS";
            if (1 == poLine.getCasePack()) {
                uom = "EA";
            }
            poLineResponse.setUom(uom);//TODO check logic
            poLineResponse.setEmployeeName(referenceDataService.getLookupByCode(LookupEnum.DSD_RCPT_LINE_EMP_NAME.name()));
            poLineResponses.add(poLineResponse);

//        }
            headerresponse.setReceiptLineResponses(poLineResponses);
        }
    }

    private void transformPoHeader(DSDReceipt receipt, ReceiptHeaderResponse dsdRecieptHeaderResponse) {
        DSDReceiptRequest dsdReceiptRequest = receipt.getLines().get(0);
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
        dsdRecieptHeaderResponse.setAttribute2(dsdReceiptRequest.getStoreId());
        dsdRecieptHeaderResponse.setAttribute3(dsdReceiptRequest.getUpcCode());
        dsdRecieptHeaderResponse.setAttribute4(dsdReceiptRequest.getRetailDepartment());
        dsdRecieptHeaderResponse.setAttribute5(dsdReceiptRequest.getStoreId() + dsdReceiptRequest.getSupplierInvoiceNo());
        dsdRecieptHeaderResponse.setAttribute7(dsdReceiptRequest.getCreatedByUserid());
        dsdRecieptHeaderResponse.setAttribute8(dsdReceiptRequest.getReceiptItemId());
        dsdRecieptHeaderResponse.setAttribute9(dsdReceiptRequest.getDescription());
        dsdRecieptHeaderResponse.setAttribute10(CommonUtility.dateToString(dsdReceiptRequest.getCreatedTs()));
    }


}