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
        return getSupplierDetails(SupplierEnum.PO).stream().collect(Collectors.toMap(Supplier::getVendorSiteCodeAlt, Function.identity(), (oldVal, newVal) -> oldVal));
    }
    @Cacheable("getReceiptSupplier")
    public Map<String, Supplier> getReceiptSupplier() {
        return getSupplierDetails(SupplierEnum.RECEIPT).stream().collect(Collectors.toMap(Supplier::getVendorSiteCodeAlt, Function.identity(), (oldVal, newVal) -> oldVal));
    }


    @Cacheable(cacheNames = "POLookup")
    public Map<String, String> getLookups() {
        log.info("getting lookup data from cached lookup");
        return restClient.getLookups().stream().collect(Collectors.toMap(Lookup::getLookupCode, Lookup::getDescription));
    }

    @Cacheable(cacheNames = "getLookupByCode")
    public String getLookupByCode(String lookupCode) {
        log.info("getting lookup data for lookupCode {}", lookupCode);
        return getLookups().get(lookupCode);
    }

    @Cacheable(cacheNames = "getPOSupplierByCode")
    public Optional<Supplier> getPOSupplierByCode(String supplierCodeAlt) {
        log.info("getting supplier data for supplierCodeAlt {}", supplierCodeAlt);
        return Optional.ofNullable(getPOSupplier().get(supplierCodeAlt));
    }
    @Cacheable(cacheNames = "getPOSupplierByCode")
    public Optional<Supplier> getReceiptSupplierByCode(String supplierCodeAlt) {
        log.info("getting supplier data for supplierCodeAlt {}", supplierCodeAlt);
        return Optional.ofNullable(getReceiptSupplier().get(supplierCodeAlt));
    }

    @Cacheable(cacheNames = "getOpenPos")
    public List<OpenPO> getOpenPos(String poNumber, POTypesEnum pType) {
        return soapClient.getOpenDetails(pType).get(poNumber);
    }

    public String getShipToLocation(String whseNo, LookupEnum lookup) {
        Optional<String> shipToLocationOpt = restClient.getSalWhseToLocLookups().stream().filter(l -> l.getTag().equalsIgnoreCase(String.valueOf(whseNo))).map(l -> l.getDescription()).findAny();

        return shipToLocationOpt.isPresent() ? shipToLocationOpt.get() : getLookupByCode(lookup.name());

    }

    public String getBuyer(String buyer, LookupEnum looup) {

        Optional<String> buyerOpt = restClient.getSalBuyerMappingLookups().stream().filter(l -> l.getTag().equalsIgnoreCase(buyer)).map(l -> l.getDescription()).findAny();

        return buyerOpt.isPresent() ? buyerOpt.get() : getLookupByCode(looup.name());

    }
}
