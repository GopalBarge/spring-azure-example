package com.sal.prompt.web.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
@Data
public class Supplier {
    @XmlAttribute(name = "SUPPLIER_NAME")
    public String supplierName;
    @XmlAttribute(name = "SUPPLIER_NUMBER")
    public String supplierNumber;
    @XmlAttribute(name = "SUPPLIER_SITE_CODE")
    public String supplierSiteCode;
    @XmlAttribute(name = "VENDOR_SITE_CODE_ALT")
    public String vendorSiteCodeAlt;
}

