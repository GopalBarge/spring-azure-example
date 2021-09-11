package com.sal.prompt.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier {
    @JsonProperty("SUPPLIER_NAME")
    public String supplierName;
    @JsonProperty("SUPPLIER_NUMBER")
    public String supplierNumber;
    @JsonProperty("SUPPLIER_SITE_CODE")
    public String supplierSiteCode;
    @JsonProperty("VENDOR_SITE_CODE_ALT")
    public String vendorSiteCodeAlt;
}

