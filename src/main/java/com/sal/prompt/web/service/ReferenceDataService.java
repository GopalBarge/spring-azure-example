package com.sal.prompt.web.service;

import com.sal.prompt.web.client.soap.SoapClient;
import com.sal.prompt.web.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReferenceDataService {

    private static final String PO_LOOKUP_PREFIX = "SAL_PO_";
    @Value("${client.rest.default-uri}")
    private String lookupHost;
    private final RestTemplate restTemplate;

    private final SoapClient soapClient;

    public void getSupplierDetails(){
        try {
//            SupplierResponse invoice = soapClient.getSupplierDetails("INVOICE");
//            List<Supplier> invoiceSupplier = invoice.getSuppliers();
            SupplierResponse po = soapClient.getSupplierDetails("PO");
            List<Supplier> poSupplier = po.getSuppliers();
            SupplierResponse receipt = soapClient.getSupplierDetails("RECEIPT");
            List<Supplier> receiptSupplier = po.getSuppliers();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    public List<Lookup> getLookup() {
        log.info("getting lookup data from rest end point");
        ResponseEntity<LookupResponse> response = restTemplate.getForEntity(lookupHost, LookupResponse.class);
        log.info("Response received {}", response.getStatusCode().toString());
        return response.getBody().getItems();
    }

    @Cacheable(cacheNames = "POLookup")
    public Map<String, String> getPOLookup() {
        log.info("getting lookup data from cached lookup");
        return getLookup().stream().filter(l -> l.getLookupCode().startsWith(PO_LOOKUP_PREFIX)).collect(Collectors.toMap(Lookup::getLookupCode, Lookup::getDescription));
    }

    @Cacheable(cacheNames = "getPOLookupByCode")
    public String getPOLookupByCode(POLookupEnum lookupCode) {
        log.info("getting lookup data for lookupCode {}", lookupCode);
        return getPOLookup().get(PO_LOOKUP_PREFIX.concat(lookupCode.name()));
    }
}
