package com.spring.azure.demo.handler;

import com.spring.azure.demo.dto.AS400Request;
import com.spring.azure.demo.dto.response.POHeaderResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public abstract class SourceDataProcessor {

    boolean process(List<AS400Request> request) {
        AtomicReference<Boolean> hasError = new AtomicReference<>(false);
        request.stream().forEach(data -> {
            POHeaderResponse response = transform(data);
            List<String> fileNames = writeToCSV(response);
            String name = zipFiles(fileNames);
            name = encrypt(name);
            boolean result = sendToStorage(name);
            if (!result) {
                hasError.set(true);
            }
        });
        return hasError.get();
    }

    private boolean sendToStorage(String name) {
        log.info("sending zip to storage location");
        return true;
    }

    private String encrypt(String name) {
        log.info("encrypting zip file");
        return "encrypted file";
    }

    private String zipFiles(List<String> fileNames) {
        log.info("ziping files");
        return "zip";
    }

    private List<String> writeToCSV(POHeaderResponse response) {
        log.info("Writing to CSV");
        return new ArrayList<>();
    }

    abstract POHeaderResponse transform(AS400Request input);
}
