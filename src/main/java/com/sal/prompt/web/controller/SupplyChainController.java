package com.sal.prompt.web.controller;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainPORequest;
import com.sal.prompt.web.dto.request.supplychain.SupplyChainReceiptRequest;
import com.sal.prompt.web.dto.request.supplychain.SupplychainReceipt;
import com.sal.prompt.web.handler.SourceDataProcessor;
import com.sal.prompt.web.handler.SupplyChainPOProcessor;
import com.sal.prompt.web.handler.SupplyChainReceiptProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supply-chain")
@RequiredArgsConstructor
public class SupplyChainController {

    private final List<SourceDataProcessor> processors;
    private final CloudBlobContainer cloudBlobContainer;

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
        Map<String, List<SupplyChainReceiptRequest>> map = requestList.stream()
                .collect(Collectors.groupingBy(SupplyChainReceiptRequest::getRecptNbr));
        List<SupplychainReceipt> request = map.entrySet().stream().map(e -> new SupplychainReceipt(e.getKey(), e.getValue())).collect(Collectors.toList());

        boolean processed = processors.stream().filter(p -> p instanceof SupplyChainReceiptProcessor).findAny().get().process(request);
        if (!processed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping(path = "/upload")
    public URI uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String constr="";


        URI uri = null;
        CloudBlockBlob blob = null;
        try {
            blob = cloudBlobContainer.getBlockBlobReference(multipartFile.getOriginalFilename());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return uri;

    }
}
