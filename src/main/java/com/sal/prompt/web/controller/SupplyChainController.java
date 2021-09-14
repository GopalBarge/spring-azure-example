package com.sal.prompt.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.sal.prompt.web.dto.SourceSystemRequest;
import com.sal.prompt.web.dto.SupplyChainRequest;
import com.sal.prompt.web.handler.SourceDataProcessor;
import com.sal.prompt.web.handler.SupplyChainProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SupplyChainController {

    private final List<SourceDataProcessor> processors;

    private final CloudBlobContainer cloudBlobContainer;
    @GetMapping
    public String sayHello() throws JAXBException, JsonProcessingException {
        return "Welcome to azure web api";
    }

    @PostMapping("/process/supply-chain")
    public ResponseEntity processSupplyChain(@RequestBody List<SupplyChainRequest> requestList) {
        boolean processed = processors.stream().filter(p -> p instanceof SupplyChainProcessor).findAny().get().process(requestList);
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
