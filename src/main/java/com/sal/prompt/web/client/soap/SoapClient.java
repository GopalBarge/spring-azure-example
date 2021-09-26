package com.sal.prompt.web.client.soap;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sal.prompt.web.client.soap.stub.*;
import com.sal.prompt.web.model.OpenPO;
import com.sal.prompt.web.model.OpenPOResponse;
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

    @Cacheable("getSupplierDetails")
    public SupplierResponse getSupplierDetails(String supplier) {
        log.info("fetching supplier data from soap service for {}", supplier);
        ArrayOfString values = new ArrayOfString();
        values.getItem().add(supplier);

        ParamNameValue paramNameValue = new ParamNameValue();
        paramNameValue.setName("p_process_name");
        paramNameValue.setValues(values);

        ArrayOfParamNameValue parameterNamesVales = new ArrayOfParamNameValue();
        parameterNamesVales.getItem().add(paramNameValue);

        ReportRequest itemRequest = new ReportRequest();
        itemRequest.setAttributeLocale("en-US");
        itemRequest.setAttributeTemplate("xml");
        itemRequest.setReportAbsolutePath("/Custom/Interfaces/Inbound/Financials/Account Payables/SAL-BIP-203 - Supplier details Report/SAL-BIP-203 - Supplier details Report.xdo");
        itemRequest.setSizeOfDataChunkDownload(-1);
        itemRequest.setParameterNameValues(parameterNamesVales);

        RunReport runReport = new RunReport();
        runReport.setReportRequest(itemRequest);

        RunReportResponse response = (RunReportResponse) webServiceTemplate.marshalSendAndReceive(defaultUri, runReport);
        return getDataFromEncodedXml(response.getRunReportReturn().getReportBytes());

    }

    @Cacheable("getOpenPODetails")
    public Map<String, List<OpenPO>> getOpenPODetails() {
        log.info("fetching open po from soap service");
//        ArrayOfString values = new ArrayOfString();
//        values.getItem().add(supplier);

//        ParamNameValue paramNameValue = new ParamNameValue();
//        paramNameValue.setName("p_process_name");
//        paramNameValue.setValues(values);

//        ArrayOfParamNameValue parameterNamesVales = new ArrayOfParamNameValue();
//        parameterNamesVales.getItem().add(paramNameValue);

        ReportRequest itemRequest = new ReportRequest();
        itemRequest.setAttributeLocale("en-US");
        itemRequest.setAttributeTemplate("xml");
        itemRequest.setReportAbsolutePath("/Custom/Interfaces/Inbound/Enrichment Reports/SAL-BIP-205 - Get PO Details - Supply Chain/SAL-BIP-205 - Get PO Details - Supply Chain Report.xdo");
        itemRequest.setSizeOfDataChunkDownload(-1);
//        itemRequest.setParameterNameValues(parameterNamesVales);

        RunReport runReport = new RunReport();
        runReport.setReportRequest(itemRequest);

        RunReportResponse response = (RunReportResponse) webServiceTemplate.marshalSendAndReceive(defaultUri, runReport);

        OpenPOResponse data = getOpenPoFromEncodedXml(response.getRunReportReturn().getReportBytes());
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
