package com.sal.prompt.web.controller;

import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainReceiptRequest;
import com.sal.prompt.web.handler.SourceDataProcessor;
import com.sal.prompt.web.handler.SupplyChainPOProcessor;
import com.sal.prompt.web.handler.SupplyChainReceiptProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/supply-chain")
@RequiredArgsConstructor
public class SupplyChainController {

    private final List<SourceDataProcessor> processors;

    @PostMapping("/process/po")
    public ResponseEntity processSupplyChainPO(@RequestBody List<SupplyChainPORequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof SupplyChainPOProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/process/receipt")
    public ResponseEntity processSupplyChainReceipt(@RequestBody List<SupplyChainReceiptRequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof SupplyChainReceiptProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
