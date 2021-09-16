package com.sal.prompt.web.controller;

import com.sal.prompt.web.dto.request.dsd.DSDPORequest;
import com.sal.prompt.web.dto.request.dsd.DSDReceiptRequest;
import com.sal.prompt.web.handler.DSDPOProcessor;
import com.sal.prompt.web.handler.DSDReceiptProcessor;
import com.sal.prompt.web.handler.SourceDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dsd")
@RequiredArgsConstructor
public class DSDController {

    private final List<SourceDataProcessor> processors;

    @PostMapping("/process/po")
    public ResponseEntity processDSDPO(@RequestBody List<DSDPORequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof DSDPOProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/process/receipt")
    public ResponseEntity processDSDReceipt(@RequestBody List<DSDReceiptRequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof DSDReceiptProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
