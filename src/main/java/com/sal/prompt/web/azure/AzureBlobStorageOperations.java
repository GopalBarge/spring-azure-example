package com.sal.prompt.web.azure;

import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

public class AzureBlobStorageOperations {

    AzureBlobConnection azureBlobConnection = null;
    CloudBlobClient blobClient = null;
    CloudBlobContainer containerName = null;

    public AzureBlobStorageOperations() {

    }

    public CloudBlobContainer getStorageContainer(String ContainerName, String connectingString) {

        try {
            azureBlobConnection = new AzureBlobConnection();
            blobClient = azureBlobConnection.getCloudBlobClient(connectingString);
            containerName = blobClient.getContainerReference(ContainerName);
            containerName.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
                    new OperationContext());

        } catch (StorageException ex) {
            System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
                    ex.getHttpStatusCode(), ex.getErrorCode()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return containerName;

    }
}
