package com.sal.prompt.web.dto.request.dsd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sal.prompt.web.dto.request.SourceSystemRequest;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DSDReceiptRequest implements SourceSystemRequest {

    @NotEmpty
    @JsonProperty("store_id")
    private String storeId;
    @NotEmpty
    @JsonProperty("receipt_id")
    private String receiptId;
    @NotEmpty
    @JsonProperty("vendor_no")
    private String vendorNo;
    @NotEmpty
    @JsonProperty("supplier_invoice_no")
    private String supplierInvoiceNo;
    @NotEmpty
    @JsonProperty("upc_code")
    private String upcCode;
    @NotEmpty
    @JsonProperty("retail_department")
    private String retailDepartment;
    @NotEmpty
    @JsonProperty("description")
    private String description;
    @NotEmpty
    @JsonProperty("case_pack")
    private String casePack;
    @NotEmpty
    @JsonProperty("qty_received")
    private Integer qtyReceived;
    @NotEmpty
    @JsonProperty("item_size")
    private String itemSize;
    @NotEmpty
    @JsonProperty("Created_by_userid")
    private String createdByUserid;
    @NotEmpty
    @JsonProperty("receipt_item_id")
    private String receiptItemId;
}
