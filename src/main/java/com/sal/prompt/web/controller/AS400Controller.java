package com.sal.prompt.web.controller;

import com.sal.prompt.web.client.soap.SoapClient;
import com.sal.prompt.web.dto.AS400Request;
import com.sal.prompt.web.handler.SourceDataProcessor;
import com.sal.prompt.web.handler.SupplyChainProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AS400Controller {

    private final List<SourceDataProcessor> processors;

    @GetMapping
    public String sayHello() {
        return "Welcome to azure web api";
    }

    @PostMapping("/process/supply-chain")
    public ResponseEntity processSupplyChain(@RequestBody List<AS400Request> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof SupplyChainProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
