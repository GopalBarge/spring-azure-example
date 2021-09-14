package com.sal.prompt.web.azure;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;

public class AzureBlobConnection {

    CloudStorageAccount storageAccount;
    CloudBlobClient blobClient = null;

    public CloudBlobClient getCloudBlobClient(String connectingString) {

        try {
            storageAccount = CloudStorageAccount.parse(connectingString);
            blobClient = storageAccount.createCloudBlobClient();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return blobClient;

    }

}
