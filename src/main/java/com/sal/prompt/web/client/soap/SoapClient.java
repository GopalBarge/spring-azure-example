package com.sal.prompt.web.client.soap;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sal.prompt.web.client.rest.RestClient;
import com.sal.prompt.web.client.soap.stub.*;
import com.sal.prompt.web.model.OpenPO;
import com.sal.prompt.web.model.OpenPOResponse;
import com.sal.prompt.web.model.POTypesEnum;
import com.sal.prompt.web.model.SupplierResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SoapClient {

    @Value("${client.soap.default-uri}")
    private String defaultUri;

    private final Jaxb2Marshaller jaxb2Marshaller;
    private final WebServiceTemplate webServiceTemplate;

    private final RestClient restClient;

//    private String SUPPLIER_DETAILS_ABSOLUTE_PATH = "/Custom/Interfaces/Inbound/Financials/Account Payables/SAL-BIP-203 - Supplier details Report/SAL-BIP-203 - Supplier details Report.xdo";
//    private String PO_DETAILS_ABSOLUTE_PATH = "/Custom/Interfaces/Inbound/Enrichment Reports/SAL-BIP-205 - Get PO Details - Supply Chain/SAL-BIP-205 - Get PO Details - Supply Chain Report.xdo";

    private String execute(String reportPath, String paramName, String paramValue) {
        ArrayOfString values = new ArrayOfString();
        values.getItem().add(paramValue);

        ParamNameValue paramNameValue = new ParamNameValue();
        paramNameValue.setName(paramName);
        paramNameValue.setValues(values);

        ArrayOfParamNameValue parameterNamesVales = new ArrayOfParamNameValue();
        parameterNamesVales.getItem().add(paramNameValue);

        ReportRequest itemRequest = new ReportRequest();
        itemRequest.setAttributeLocale("en-US");
        itemRequest.setAttributeTemplate("xml");
        itemRequest.setReportAbsolutePath(reportPath);
        itemRequest.setSizeOfDataChunkDownload(-1);
        itemRequest.setParameterNameValues(parameterNamesVales);

        RunReport runReport = new RunReport();
        runReport.setReportRequest(itemRequest);

        RunReportResponse response = (RunReportResponse) webServiceTemplate.marshalSendAndReceive(defaultUri, runReport);
        return response.getRunReportReturn().getReportBytes();
    }

    private String executeRequest(String payload) {
        String result = "";
        try {
            String xml = restClient.executeSoapRequest(payload);
            XmlMapper xmlMapper = new XmlMapper();

            JsonNode node = xmlMapper.readTree(xml.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(node);
            result = jsonMapper.readTree(json).get("Body").get("runReportResponse").get("runReportReturn").get("reportBytes").asText();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 1. Interface - SAL-FIN-180 - PO Interface from AS400
     * <p>
     * PO_Prompt_Header_FTP
     * <p>
     * Report -  (get supplier, supplier number, supplier site)
     * <p>
     * 2. Interface - SAL-FIN-181 - PO Receipts Interface from AS400
     * <p>
     * PO_Prompt_Receipt_FTP_RCV_HDR
     * <p>
     * Report - (get supplier, supplier number, supplier site)
     */

    @Cacheable("getSupplierDetails")
    public SupplierResponse getSupplierDetails(String supplier) {
        log.info("fetching supplier data from soap service for {}", supplier);
        String payload = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\">" +
                "    <soap:Header/>" +
                "    <soap:Body>" +
                "        <pub:runReport>" +
                "            <pub:reportRequest>" +
                "                <pub:attributeLocale>en-US</pub:attributeLocale>" +
                "                <pub:attributeFormat>xml</pub:attributeFormat>" +
                "                <pub:reportAbsolutePath>/Custom/Interfaces/Inbound/Financials/Account Payables/SAL-BIP-203 - Supplier details Report/SAL-BIP-203 - Supplier details Report.xdo</pub:reportAbsolutePath>" +
                "                <pub:sizeOfDataChunkDownload>-1</pub:sizeOfDataChunkDownload>" +
                "                <pub:parameterNameValues>" +
                "                    <pub:item>" +
                "                        <pub:name>p_process_name</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item>" + supplier + "</pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                    <pub:item>" +
                "                        <pub:name>p_alt_dun_nbr</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item></pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                </pub:parameterNameValues>" +
                "            </pub:reportRequest>" +
                "        </pub:runReport>" +
                "    </soap:Body>" +
                "</soap:Envelope>";

//        String response = execute(SUPPLIER_DETAILS_ABSOLUTE_PATH, "p_process_name", supplier);
        return getDataFromEncodedXml(executeRequest(payload));

    }

    /**
     * 2. Interface - SAL-FIN-181 - PO Receipts Interface from AS400
     * <p>
     * <p>
     * PO_Prompt_Receipt_FTP_RCV_HDR
     * <p>
     * Report - (get supplier, supplier number, supplier site)
     */
    @Cacheable("getOpenPODetails")
    public Map<String, List<OpenPO>> getOpenDetails(POTypesEnum pType) {
        String payload = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\"><soap:Header/>" +
                "    <soap:Body>" +
                "        <pub:runReport>" +
                "            <pub:reportRequest>" +
                "                <pub:attributeLocale>en-US</pub:attributeLocale>" +
                "                <pub:reportAbsolutePath>/Custom/Interfaces/Inbound/Enrichment Reports/SAL-BIP-205 - Get PO Details - Supply Chain/SAL-BIP-205 - Get PO Details - Supply Chain Report.xdo</pub:reportAbsolutePath>" +
                "                <pub:sizeOfDataChunkDownload>-1</pub:sizeOfDataChunkDownload>" +
                "                <pub:parameterNameValues>" +
                "                    <pub:item>" +
                "                        <pub:name>P_Type</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item>" + pType.name() + "</pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                    <pub:item>" +
                "                        <pub:name>P_PO_No</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item></pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                </pub:parameterNameValues>" +
                "                <pub:parameterNameValues>" +
                "                    <pub:item>" +
                "                        <pub:name>P_Vendor_no</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item></pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                </pub:parameterNameValues>" +
                "            </pub:reportRequest>" +
                "        </pub:runReport>" +
                "    </soap:Body>" +
                "</soap:Envelope>";

        OpenPOResponse data = getOpenPoFromEncodedXml(executeRequest(payload));
        return data.getPos().stream().collect(Collectors.groupingBy(OpenPO::getPoNbr));
    }

    /**
     * PO_Prompt_Detail_FTP -
     * Report -  (Get open PO with PO lines (Include open and close lines) in case of AS400 PO Update operation
     */
    @Cacheable("getOpenPODetails")
    public Map<String, List<OpenPO>> getOpenCloseDetails(POTypesEnum pType) {
        log.info("fetching open po from soap service");

//        String response = execute(PO_DETAILS_ABSOLUTE_PATH, "P_Type", pType.name());
        String payload = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\"> <soap:Header/>" +
                "    <soap:Body>" +
                "        <pub:runReport>" +
                "            <pub:reportRequest>" +
                "                <pub:attributeLocale>en-US</pub:attributeLocale>" +
                "                <pub:reportAbsolutePath>/Custom/Interfaces/Inbound/Enrichment Reports/SAL-BIP-205 - Get PO Details -" +
                "                    Supply Chain/SAL-BIP-205 - Get PO Details - Supply Chain Report.xdo" +
                "                </pub:reportAbsolutePath>" +
                "                <pub:sizeOfDataChunkDownload>-1</pub:sizeOfDataChunkDownload>" +
                "                <pub:parameterNameValues>" +
                "                    <pub:item>" +
                "                        <pub:name>P_Type</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item>" + pType.name() + "</pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                    <pub:item>" +
                "                        <pub:name>P_PO_No</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item></pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                </pub:parameterNameValues>" +
                "                <pub:parameterNameValues>" +
                "                    <pub:item>" +
                "                        <pub:name>P_Vendor_no</pub:name>" +
                "                        <pub:values>" +
                "                            <pub:item></pub:item>" +
                "                        </pub:values>" +
                "                    </pub:item>" +
                "                </pub:parameterNameValues>" +
                "            </pub:reportRequest>" +
                "        </pub:runReport>" +
                "    </soap:Body>" +
                "</soap:Envelope>";

        OpenPOResponse data = getOpenPoFromEncodedXml(executeRequest(payload));
        return data.getPos().stream().collect(Collectors.groupingBy(OpenPO::getPoNbr));

    }

    private OpenPOResponse getOpenPoFromEncodedXml(String encodedXml) {
        Base64.Decoder decoder = Base64.getMimeDecoder();
        String xml = new String(decoder.decode(encodedXml));
        XmlMapper xmlMapper = new XmlMapper();
        OpenPOResponse result = null;
        try {
            JsonNode node = xmlMapper.readTree(xml.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(node);
            result = jsonMapper.readValue(json, OpenPOResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private SupplierResponse getDataFromEncodedXml(String encodedXml) {
        Base64.Decoder decoder = Base64.getMimeDecoder();
        String xml = new String(decoder.decode(encodedXml));
        XmlMapper xmlMapper = new XmlMapper();
        SupplierResponse result = null;
        try {
            JsonNode node = xmlMapper.readTree(xml.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(node);
            result = jsonMapper.readValue(json, SupplierResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
