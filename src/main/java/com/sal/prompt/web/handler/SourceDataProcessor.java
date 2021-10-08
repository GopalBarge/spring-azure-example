package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.dto.response.TargetSystemResponse;
import com.sal.prompt.web.service.FBDIFormatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class SourceDataProcessor {

    private final FBDIFormatService fbdiFormatService;

    abstract String getSourceSystem();
    abstract String getTargetSystem();


    public boolean process(List<? extends SourceSystemRequest> request,String batchId) {
        AtomicReference<Boolean> hasError = new AtomicReference<>(false);
        List<SourceSystemRequest> failedRecords = new ArrayList<>();
        List<TargetSystemResponse> transformedData = request.stream().map(data -> {
            try {
                return transform(data,batchId);
            } catch (Exception e) {
                failedRecords.add(data);
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        Map<String, List> dataToWrite =  getFileDataMap(transformedData);
        String zipFileName = String.format("%s_%s.zip",getSourceSystem(), getTargetSystem(),batchId);
        boolean isUploaded = fbdiFormatService.prepareFBDIFiles(dataToWrite, zipFileName);

        if (!isUploaded) {
            hasError.set(true);
        }
        return !hasError.get();
    }

    protected abstract Map<String, List> getFileDataMap(List<TargetSystemResponse> transformedData);


    protected abstract TargetSystemResponse transform(SourceSystemRequest input, String batchId) throws Exception;
}
