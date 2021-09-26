package com.sal.prompt.web.client.rest;

import com.sal.prompt.web.model.Lookup;
import com.sal.prompt.web.model.LookupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {
    @Value("${client.rest.default-uri}")
    private String lookupHost;
    private final RestTemplate restTemplate;

    @Cacheable(cacheNames = "getSupplyChainLookup")
    public List<Lookup> getSupplyChainLookup() {
        log.info("getting lookup data from rest end point");
        String promptLookupUrl = lookupHost + "?q=LookupType=PROMPT_TEST";
        ResponseEntity<LookupResponse> response = restTemplate.getForEntity(lookupHost, LookupResponse.class);
        log.info("Response received {}", response.getStatusCode().toString());
        return response.getBody().getItems();
    }

    @Cacheable(cacheNames = "getDSDLookup")
    public List<Lookup> getDistributionLookup() {
        log.info("getting lookup data from rest end point");
        String promptLookupUrl = lookupHost + "?q=LookupType=SAL_PROMPT_REPL_GL_LKP";
        ResponseEntity<LookupResponse> response = restTemplate.getForEntity(lookupHost, LookupResponse.class);
        log.info("Response received {}", response.getStatusCode().toString());
        return response.getBody().getItems();
    }

    @Cacheable(cacheNames = "getSalWhseToLocLookups")
    public List<Lookup> getSalWhseToLocLookups() {
        return getLookupResponse(lookupHost + "SAL_WHSE_TO_LOC_LKP" + "&limit=300");
    }

    @Cacheable(cacheNames = "getSalBuyerMappingLookups")
    public List<Lookup> getSalBuyerMappingLookups() {
        return getLookupResponse(lookupHost + "SAL_BUYER_MAPPING_LKP" + "&limit=300");
    }

    @Cacheable(cacheNames = "getLookups")
    public List<Lookup> getLookups() {
        log.info("getting lookup data from rest end point");
        List<Lookup> salPromptReplLkp = getLookupResponse(lookupHost + "SAL_PROMPT_REPL_LKP" + "&limit=300");
        List<Lookup> salPromptBuyerLkp = getLookupResponse(lookupHost + "SAL_PROMPT_BUYER_LKP" + "&limit=300");
        List<Lookup> salPromptReplGlLkp = getLookupResponse(lookupHost + "SAL_PROMPT_REPL_GL_LKP" + "&limit=300");

        List<Lookup> lookups = new ArrayList<>();
        lookups.addAll(salPromptReplLkp);
        lookups.addAll(salPromptBuyerLkp);
        lookups.addAll(salPromptReplGlLkp);
        return lookups;
    }

    public List<Lookup> getLookupResponse(String url) {
        ResponseEntity<LookupResponse> response = restTemplate.getForEntity(url, LookupResponse.class);
        log.info("Response received {}", response.getStatusCode().toString());
        return response.getBody().getItems();
    }

}
