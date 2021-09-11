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

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {
    @Value("${client.rest.default-uri}")
    private String lookupHost;
    private final RestTemplate restTemplate;

    @Cacheable(cacheNames = "getLookup")
    public List<Lookup> getLookup() {
        log.info("getting lookup data from rest end point");
        ResponseEntity<LookupResponse> response = restTemplate.getForEntity(lookupHost, LookupResponse.class);
        log.info("Response received {}", response.getStatusCode().toString());
        return response.getBody().getItems();
    }
}
