package com.sal.prompt.web.controller;

import com.sal.prompt.web.handler.SourceDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DSDController {

    private final List<SourceDataProcessor> processors;

   /* @PostMapping("/process/DSD")
    public ResponseEntity processDSD(@RequestBody List<DSDRequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof DSDSupplyChainProcessor).findAny().get().process(requestList);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }*/
}
