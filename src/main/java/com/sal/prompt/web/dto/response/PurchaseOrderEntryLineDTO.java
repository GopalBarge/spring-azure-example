package com.sal.prompt.web.dto.response;

import lombok.Data;

@Data
public class PurchaseOrderEntryLineDTO {
    public String action;
    public String lineNumber;
    public String price;
    public String quantity;
}
