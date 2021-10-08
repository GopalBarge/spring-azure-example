package com.sal.prompt.web.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class POUpdateDTO {
    public String pOHeaderId;
    public String changeOrderDescription;
    public String paymentTerms;
    public List<PurchaseOrderEntryLineDTO> purchaseOrderEntryLines;
}


