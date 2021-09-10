package com.sal.prompt.web.client.soap;


import com.sal.prompt.web.client.soap.stub.ReportRequest;
import com.sal.prompt.web.client.soap.stub.RunReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.TransportConstants;

import javax.xml.bind.JAXBElement;
import javax.xml.soap.MimeHeaders;

@Service
public class SoapClient {
    @Autowired
    WebServiceTemplate webServiceTemplate;

    public void getSupplierDetails() {

        RunReport runReport = new RunReport();
        ReportRequest reportRequest = new ReportRequest();
//        reportRequest.setReportData(getPayLoad().getBytes());
//        runReport.setReportRequest(reportRequest);
        JAXBElement res = (JAXBElement) webServiceTemplate.marshalSendAndReceive(runReport);

        System.out.println("res = " + res);
    }

    private WebServiceMessageCallback getWebServiceMessageCallback() {
        return webServiceMessage -> {
            SaajSoapMessage soapMessage = (SaajSoapMessage) webServiceMessage;
            MimeHeaders headers = soapMessage.getSaajMessage().getMimeHeaders();
            headers.setHeader(TransportConstants.HEADER_CONTENT_TYPE, "application/soap+xml; charset=utf-8");
            headers.setHeader("Accept", "application/soap+xml; charset=utf-8");
        };
    }
}