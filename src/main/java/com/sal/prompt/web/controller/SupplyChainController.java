package com.sal.prompt.web.controller;

import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainReceiptRequest;
import com.sal.prompt.web.dto.request.supplychain.SupplychainReceipt;
import com.sal.prompt.web.handler.SourceDataProcessor;
import com.sal.prompt.web.handler.SupplyChainPOProcessor;
import com.sal.prompt.web.handler.SupplyChainReceiptProcessor;
import com.sal.prompt.web.utils.CommonUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supply-chain")
@RequiredArgsConstructor
public class SupplyChainController {

    private final List<SourceDataProcessor> processors;

    @PostMapping("/process/po")
    public ResponseEntity processSupplyChainPO(@RequestBody List<SupplyChainPORequest> requestList) {
        String batchId = CommonUtility.getBatchId();

        boolean processed = processors.stream().filter(p -> p instanceof SupplyChainPOProcessor).findAny().get().process(requestList, batchId);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/process/receipt")
    public ResponseEntity processSupplyChainReceipt(@RequestBody List<SupplyChainReceiptRequest> requestList) {

        String batchId = CommonUtility.getBatchId();

        Map<String, List<SupplyChainReceiptRequest>> map = requestList.stream()
                .collect(Collectors.groupingBy(SupplyChainReceiptRequest::getRecptNbr));
        List<SupplychainReceipt> request = map.entrySet().stream().map(e -> new SupplychainReceipt(e.getKey(), e.getValue())).collect(Collectors.toList());

        boolean processed = processors.stream().filter(p -> p instanceof SupplyChainReceiptProcessor).findAny().get().process(request, batchId);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
