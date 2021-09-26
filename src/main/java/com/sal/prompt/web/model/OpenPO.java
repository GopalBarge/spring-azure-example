package com.sal.prompt.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenPO {
    @JsonProperty("DOCUMENT_NUMBER")
    public String documentNumber;
    @JsonProperty("PO_NBR")
    public String poNbr;
    @JsonProperty("VENDOR_NBR")
    public String vendorNbr;
    @JsonProperty("VENDOR_FAC")
    public String vendorFac;
    @JsonProperty("LINE_NUM")
    public String lineNum;
    @JsonProperty("ITEM_FACILITY")
    public String itemFacility;
    @JsonProperty("ITEM_NBR")
    public String itemNbr;
    @JsonProperty("ITEM_UPC")
    public String itemUpc;
}

