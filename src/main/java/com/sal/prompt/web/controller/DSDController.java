package com.sal.prompt.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.sal.prompt.web.dto.request.dsd.DSDPORequest;
import com.sal.prompt.web.dto.request.dsd.DSDReceipt;
import com.sal.prompt.web.dto.request.dsd.DSDReceiptRequest;
import com.sal.prompt.web.handler.DSDPOProcessor;
import com.sal.prompt.web.handler.DSDReceiptProcessor;
import com.sal.prompt.web.handler.SourceDataProcessor;
import com.sal.prompt.web.utils.CommonUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dsd")
@RequiredArgsConstructor
public class DSDController {

    private final List<SourceDataProcessor> processors;
    private final CloudBlobContainer cloudBlobContainer;


    @GetMapping("/callback/{interfaceType}/{batchId}")
    public ResponseEntity processDSDCallback(@PathVariable("interfaceType") String interfaceType,
                                             @PathVariable("batchId") String batchId) {
        Gson gson = new GsonBuilder().create();
        String fileName = "DSD_" + batchId + ".json";
        String requestStr = getFileContent(fileName);
        DSDReceiptRequest[] dtos = gson.fromJson(requestStr, DSDReceiptRequest[].class);
        List<DSDReceiptRequest> requestList = Arrays.stream(dtos).collect(Collectors.toList());
        if ("DSD_PO".equalsIgnoreCase(interfaceType)) {
            processDSDReceipt(requestList, batchId);
        }
        if ("DSD_RECEIPT".equalsIgnoreCase(interfaceType)) {
//            processDSDInvoice(requestList,batchId);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/process")
    public ResponseEntity processDSD(@RequestBody List<DSDPORequest> requestList) throws IOException {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(requestList);
        String batchId = CommonUtility.getBatchId();
        String fileName = "DSD_" + batchId + ".json";

        uploadFile(json, fileName);
        String requestStr = getFileContent(fileName);
        DSDPORequest[] dtos = gson.fromJson(requestStr, DSDPORequest[].class);
        requestList = Arrays.stream(dtos).collect(Collectors.toList());
        return processDSDPO(requestList, batchId);

    }


    @PostMapping("/process/po")
    public ResponseEntity processDSDPO(@RequestBody List<DSDPORequest> requestList, String batchId) {

        Map<String, List<DSDPORequest>> map = requestList.stream()
                .collect(Collectors.groupingBy(DSDPORequest::getReceiptId));
        List<DSDPORequest> poRequest = new ArrayList<>(map.size());
        map.entrySet().stream().forEach(d -> poRequest.add(d.getValue().get(0)));
        boolean processed = processors.stream().filter(p -> p instanceof DSDPOProcessor).findAny().get().process(poRequest, batchId);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/process/receipt")
    public ResponseEntity processDSDReceipt(@RequestBody List<DSDReceiptRequest> requestList, String batchId) {

        Map<String, List<DSDReceiptRequest>> map = requestList.stream()
                .collect(Collectors.groupingBy(DSDReceiptRequest::getReceiptId));
        List<DSDReceipt> request = map.entrySet().stream().map(e -> new DSDReceipt(e.getKey(), e.getValue())).collect(Collectors.toList());


        boolean processed = processors.stream().filter(p -> p instanceof DSDReceiptProcessor).findAny().get().process(request, batchId);

        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public URI uploadFile(String jsonContent, String fileName) {
        URI uri = null;
        CloudBlockBlob blob = null;
        try {
            blob = cloudBlobContainer.getBlockBlobReference(fileName);
            blob.upload(new ByteArrayInputStream(jsonContent.getBytes()), -1);
            uri = blob.getUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    public String getFileContent(String fileName) {
        String jsonContent = null;
        CloudBlockBlob blob = null;
        try {
            blob = cloudBlobContainer.getBlockBlobReference(fileName);
            jsonContent = blob.downloadText();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonContent;
    }
}
