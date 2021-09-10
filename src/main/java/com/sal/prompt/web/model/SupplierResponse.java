package com.sal.prompt.web.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;
@Data
public class SupplierResponse {
    /*public String P_PROCESS_NAME;
    public Object P_VENDOR_NBR;*/
    @XmlElement(name = "G_1")
    public List<Supplier> suppliers;
}
