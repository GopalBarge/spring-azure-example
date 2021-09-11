package com.sal.prompt.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierResponse {
    @JsonProperty("P_PROCESS_NAME")
    public String pProcessName;
    @JsonProperty("P_VENDOR_NBR")
    public String pVendorNbr;
    @JsonProperty("G_1")
    public List<Supplier> suppliers;
}
