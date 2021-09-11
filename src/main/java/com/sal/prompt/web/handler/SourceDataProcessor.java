package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.AS400Request;
import com.sal.prompt.web.dto.response.POHeaderResponse;
import com.sal.prompt.web.service.FBDIFormatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class SourceDataProcessor {

    private final FBDIFormatService fbdiFormatService;

    public boolean process(List<AS400Request> request) {
        AtomicReference<Boolean> hasError = new AtomicReference<>(false);
        List<POHeaderResponse> transformedData = request.stream().map(data -> transform(data)).collect(Collectors.toList());

        List<String> fileNames = fbdiFormatService.prepareFBDIFiles(transformedData);
        String name = zipFiles(fileNames);
        name = encrypt(name);
        boolean result = sendToStorage(name);
        if (!result) {
            hasError.set(true);
        }
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

    abstract POHeaderResponse transform(AS400Request input);
}
