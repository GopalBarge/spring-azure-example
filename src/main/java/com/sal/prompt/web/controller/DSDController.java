package com.sal.prompt.web.controller;

import com.sal.prompt.web.dto.request.dsd.DSDPORequest;
import com.sal.prompt.web.dto.request.dsd.DSDReceipt;
import com.sal.prompt.web.dto.request.dsd.DSDReceiptRequest;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainReceiptRequest;
import com.sal.prompt.web.dto.request.supplychain.SupplychainReceipt;
import com.sal.prompt.web.handler.DSDPOProcessor;
import com.sal.prompt.web.handler.DSDReceiptProcessor;
import com.sal.prompt.web.handler.SourceDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dsd")
@RequiredArgsConstructor
public class DSDController {

    private final List<SourceDataProcessor> processors;

    @PostMapping("/process")
    public ResponseEntity processDSD(@RequestBody List<DSDPORequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof DSDPOProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/callback/{interfaceType}")
    public ResponseEntity processDSDCallback(@RequestBody List<DSDPORequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof DSDPOProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

//    @PostMapping("/process/po")
    public ResponseEntity processDSDPO(@RequestBody List<DSDPORequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof DSDPOProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

//    @PostMapping("/process/receipt")
    public ResponseEntity processDSDReceipt(@RequestBody List<DSDReceiptRequest> requestList) {

        Map<String, List<DSDReceiptRequest>> map = requestList.stream()
                .collect(Collectors.groupingBy(DSDReceiptRequest::getReceiptId));
        List<DSDReceipt> request = map.entrySet().stream().map(e -> new DSDReceipt(e.getKey(), e.getValue())).collect(Collectors.toList());

        boolean processed = processors.stream().filter(p -> p instanceof DSDReceiptProcessor).findAny().get().process(request);

        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
