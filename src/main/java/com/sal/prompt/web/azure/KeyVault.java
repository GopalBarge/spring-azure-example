package com.sal.prompt.web.azure;

import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.SecretBundle;

public class KeyVault {

    public String GetSecretFromVault(String name) {

        String keyVaultName = System.getenv("KEY_VAULT_NAME");

        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";

        KeyVaultClient client = new KeyVaultClient(new ClientSecretKeyVaultCredential(System.getenv("AZURE_CLIENT_ID"),
                System.getenv("AZURE_CLIENT_SECRET")));

        SecretBundle secret = client.getSecret(keyVaultUri, name);
        return secret.value();
    }
}
