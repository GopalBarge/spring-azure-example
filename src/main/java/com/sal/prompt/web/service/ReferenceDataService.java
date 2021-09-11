package com.sal.prompt.web.service;

import com.sal.prompt.web.client.rest.RestClient;
import com.sal.prompt.web.client.soap.SoapClient;
import com.sal.prompt.web.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReferenceDataService {

    private static final String PO_LOOKUP_PREFIX = "SAL_PO_";


    private final SoapClient soapClient;
    private final RestClient restClient;

    public List<Supplier> getSupplierDetails(SupplierEnum supplier) {
        log.info("fetching supplier details for {}", supplier);
        SupplierResponse invoice = soapClient.getSupplierDetails(supplier.name());
        return invoice.getSuppliers();

    }

    @Cacheable("getPOSupplier")
    public Map<String, Supplier> getPOSupplier() {
        //TODO getting duplicate key 30372
        return getSupplierDetails(SupplierEnum.PO).stream().collect(Collectors.toMap(Supplier::getVendorSiteCodeAlt, Function.identity(), (oldVal, newVal) -> oldVal));
    }

    @Cacheable("getInvoiceSupplier")
    public Map<String, Supplier> getInvoiceSupplier() {
        return getSupplierDetails(SupplierEnum.INVOICE).stream().collect(Collectors.toMap(Supplier::getVendorSiteCodeAlt, Function.identity(), (oldVal, newVal) -> oldVal));
    }

    @Cacheable("getReceiptSupplier")
    public Map<String, Supplier> getReceiptSupplier() {
        return getSupplierDetails(SupplierEnum.RECEIPT).stream().collect(Collectors.toMap(Supplier::getVendorSiteCodeAlt, Function.identity(), (oldVal, newVal) -> oldVal));
    }


    @Cacheable(cacheNames = "POLookup")
    public Map<String, String> getPOLookup() {
        log.info("getting lookup data from cached lookup");
        return restClient.getLookup().stream().filter(l -> l.getLookupCode().startsWith(PO_LOOKUP_PREFIX)).collect(Collectors.toMap(Lookup::getLookupCode, Lookup::getDescription));
    }

    @Cacheable(cacheNames = "getPOLookupByCode")
    public String getPOLookupByCode(POLookupEnum lookupCode) {
        log.info("getting lookup data for lookupCode {}", lookupCode);
        return getPOLookup().get(PO_LOOKUP_PREFIX.concat(lookupCode.name()));
    }

    @Cacheable(cacheNames = "getPOSupplierByCode")
    public Optional<Supplier> getPOSupplierByCode(String supplierCodeAlt) {
        log.info("getting supplier data for supplierCodeAlt {}", supplierCodeAlt);
        return Optional.ofNullable(getPOSupplier().get(supplierCodeAlt));
    }
}
