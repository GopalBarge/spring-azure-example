package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.request.dsd.DSDReceipt;
import com.sal.prompt.web.dto.request.dsd.DSDReceiptRequest;
import com.sal.prompt.web.dto.request.supplychain.PoLines;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainReceiptRequest;
import com.sal.prompt.web.dto.request.supplychain.SupplychainReceipt;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.LookupEnum;
import com.sal.prompt.web.model.OpenPO;
import com.sal.prompt.web.model.POTypesEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.model.lookup.SupplyChainPOLineLookupEnum;
import com.sal.prompt.web.model.lookup.SupplyChainPOLookupEnum;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import com.sal.prompt.web.utils.CommonUtility;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.sal.prompt.web.utils.CommonUtility.dateToString;
import static com.sal.prompt.web.utils.CommonUtility.getDate;

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
    public TargetSystemResponse transform(SourceSystemRequest request, String batchId) throws Exception {
        ReceiptHeaderResponse poHeaderResponse = new ReceiptHeaderResponse();
        if (request instanceof SupplychainReceipt) {
            SupplychainReceipt input = (SupplychainReceipt) request;
            transformPoHeader(input, poHeaderResponse);
            transformPoLine(input, poHeaderResponse);
        }
        return poHeaderResponse;
    }

    private void transformPoLine(SupplychainReceipt receipt, ReceiptHeaderResponse response) throws Exception {
        List<ReceiptLineResponse> poLineResponses = new ArrayList<>();
        Integer lineNum = 0;

        for (SupplyChainReceiptRequest poLine : receipt.getLines()) {
            lineNum = lineNum + 1;
            ReceiptLineResponse poLineResponse = new ReceiptLineResponse();
            poLineResponse.setInterfaceLineNumber(String.valueOf(lineNum));
            poLineResponse.setTransactionType(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_LINE_TXN_TYPE.name()));
            poLineResponse.setAutoTransactCode(dateToString(poLine.getRcvDate()));//TODO check logic no Lookup present
            poLineResponse.setSourceDocumentCode(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_LINE_SOURCE_DOC_CODE.name()));
            poLineResponse.setReceiptSourceCode(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_LINE_SOURCE_CODE.name()));
            poLineResponse.setHeaderInterfaceNumber(response.getHeaderInterfaceNumber());
            poLineResponse.setItemDescription(poLine.getItemDesc());
            List<OpenPO> openPos = referenceDataService.getOpenPos(poLine.getPoNbr(), POTypesEnum.AS400); //TODO check p_type for AS400 receipt
            Optional<String> documentNumber = openPos.stream().filter(po ->
                    po.getVendorNbr().equalsIgnoreCase(poLine.getVendorNbr())
                    && po.getItemFacility().equalsIgnoreCase(poLine.getItemFacility())).map(po -> po.getDocumentNumber()).findFirst();
            if(!documentNumber.isPresent()){
                throw new Exception("Document number not found");
            }
            Optional<String> documentLine = openPos.stream().filter(po -> (
                    po.getItemFacility().equalsIgnoreCase(poLine.getItemFacility()) &&
                    po.getItemNbr().equalsIgnoreCase(poLine.getItemNbr()) &&
                    po.getItemUpc().equalsIgnoreCase(String.valueOf(poLine.getItemUpc())))
             ).map(po -> po.getDocumentNumber()).findFirst();
            if(!documentLine.isPresent()){
                throw new Exception("Document line not found");
            }

            poLineResponse.setDocumentNumber(documentNumber.get());
             poLineResponse.setDocumentLineNumber(documentLine.get());
            poLineResponse.setDocumentScheduleNumber(poLineResponse.getDocumentLineNumber());
            poLineResponse.setDocumentDistributionNumber(poLineResponse.getDocumentLineNumber());
            poLineResponse.setBusinessUnit(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_LINE_BU.name()));
            poLineResponse.setExpectedReceiptDate(CommonUtility.dateToString(poLine.getRcvDate()));

            Integer quantity = poLine.getOnOrder();
            String uom = "CS";
            if ("LBS".equalsIgnoreCase(poLine.getHiItemSize()) || "LB AVG".equalsIgnoreCase(poLine.getHiItemSize())) {
//                if (Integer.valueOf(poLine.getItemLstCost()) <= 7) { //TODO check logic again its same for < or > and what in case of equal to 7
                    quantity = poLine.getItemPack() * poLine.getOnOrder();
                    uom = "LBS";
//                } else {
//                    quantity = poLine.getItemPack() * poLine.getOnOrder();
//                    uom = "LBS";
//                }
            }

                poLineResponse.setQuantity(String.valueOf(quantity));
                poLineResponse.setUom(uom);
                poLineResponse.setEmployeeName(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_LINE_EMP_NAME.name()));
                poLineResponses.add(poLineResponse);

                response.setReceiptLineResponses(poLineResponses);
            }
        }

        private void transformPoHeader(SupplychainReceipt receipt, ReceiptHeaderResponse response){
            SupplyChainReceiptRequest request = receipt.getLines().get(0);
            response.setHeaderInterfaceNumber(getRandomNumber());
            response.setReceiptSourceCode(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_SOURCE_CODE.name()));
            response.setASNType(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_ASN_TYPE.name()));
            response.setTransactionType(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_TXN_TYPE.name()));
            Optional<Supplier> supplierOpt = referenceDataService.getReceiptSupplierByCode(request.getVendorNbr());
            if (supplierOpt.isPresent()) {
                Supplier supplier = supplierOpt.get();
                response.setSupplierName(supplier.getSupplierName());
                response.setSupplierSiteCode(supplier.getSupplierSiteCode());
            }

            response.setEmployeeName(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_EMP_NAME.name()));
            response.setTransactionDate(getDate());//TODO heck again if sysdate logic is correct
            response.setBusinessUnit(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_BU.name()));
            response.setAttributeCategory(referenceDataService.getLookupByCode(LookupEnum.SC_RCPT_ATTRIBUTE_CAT.name()));
            response.setAttribute1(request.getPoNbr());
            response.setAttribute2(request.getRecptNbr());
            response.setAttribute3(request.getDepartment());
            response.setAttribute4(request.getDepartName());
            response.setAttribute5(String.valueOf(request.getQtyOrdered()));
            response.setAttribute7(String.valueOf(request.getItemUpc()));
            response.setAttribute8(String.valueOf(request.getLastCost()));
            response.setAttribute9(request.getItemNbr());
            response.setAttribute10(request.getMerchNbr());
            response.setAttribute11(request.getBuyerNbr());

            response.setAttributeDate1(dateToString(request.getRcvDate()));
        }
    }
