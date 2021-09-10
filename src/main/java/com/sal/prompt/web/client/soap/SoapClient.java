package com.sal.prompt.web.client.soap;


import com.sal.prompt.web.client.soap.stub.*;
import com.sal.prompt.web.model.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.xml.transform.StringSource;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.util.Base64;

@Service
public class SoapClient {

    @Value("${client.soap.default-uri}")
    private String defaultUri;

    @Autowired
    private Jaxb2Marshaller jaxb2Marshaller;
    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public SupplierResponse getSupplierDetails(String supplier) throws JAXBException {
        ReportRequest itemRequest = new ReportRequest();
        itemRequest.setAttributeLocale("en-US");
        itemRequest.setAttributeTemplate("xml");
        itemRequest.setReportAbsolutePath("/Custom/Interfaces/Inbound/Financials/Account Payables/SAL-BIP-203 - Supplier details Report/SAL-BIP-203 - Supplier details Report.xdo");
        itemRequest.setSizeOfDataChunkDownload(-1);
        ArrayOfParamNameValue parameterNamesVales = new ArrayOfParamNameValue();
        ParamNameValue paramNameValue = new ParamNameValue();
        paramNameValue.setName("p_process_name");

        ArrayOfString values = new ArrayOfString();
        values.getItem().add(supplier);
        paramNameValue.setValues(values);
        //getValues().getItem().add("RECEIPT");
        parameterNamesVales.getItem().add(paramNameValue);
        itemRequest.setParameterNameValues(parameterNamesVales);
        RunReport runReport = new RunReport();
        runReport.setReportRequest(itemRequest);
//        webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
        RunReportResponse response = (RunReportResponse) webServiceTemplate.marshalSendAndReceive(defaultUri, runReport);
        String decoded = decode(response.getRunReportReturn().getReportBytes());
        JAXBElement<SupplierResponse> result = jaxb2Marshaller.createUnmarshaller().unmarshal(new StringSource(decoded), SupplierResponse.class);
        return result.getValue();

    }

    private String decode(String reportBytes) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(reportBytes));
    }
}
