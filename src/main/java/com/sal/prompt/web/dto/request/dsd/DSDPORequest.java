package com.sal.prompt.web.dto.request.dsd;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.utils.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class DSDPORequest implements SourceSystemRequest {
    @NotEmpty
    @JsonProperty("receipt_id")
    private String receiptId;
    @NotEmpty
    @JsonProperty("created_by_userid")
    private String createdByUserid;
    @NotEmpty
    @JsonProperty("vendor_no")
    private String vendorNo;
    @NotEmpty
    @JsonProperty("description")
    private String description;
    @NotEmpty
    @JsonProperty("term_percent")
    private String termPercent;
    @NotEmpty
    @JsonProperty("term_days")
    private String termDays;
    @NotEmpty
    @JsonProperty("term_net_days")
    private String termNetDays;
    @NotEmpty
    @JsonProperty("store_id")
    private String storeId;
    @NotEmpty
    @JsonProperty("supplier_invoice_no")
    private String supplierInvoiceNo;
    @NotEmpty
    @JsonProperty("receipt_item_id")
    private String receiptItemId;
    @NotEmpty
    @JsonProperty("upc_code")
    private String upcCode;
    @NotEmpty
    @JsonProperty("retail_department")
    private String retailDepartment;
    @NotEmpty
    @JsonProperty("retail_class")
    private String retailClass;
    @NotEmpty
    @JsonProperty("item_size")
    private String itemSize;
    @NotEmpty
    @JsonProperty("uom_descriptor")
    private String uomDescriptor;
    @NotEmpty
    @JsonProperty("case_pack")
    private String casePack;
    @NotEmpty
    @JsonProperty("item_cost")
    private Integer itemCost;
    @NotEmpty
    @JsonProperty("retail_qty")
    private Integer retailQty;
    @NotEmpty
    @JsonProperty("retail_price")
    private Integer retailPrice;
    @NotEmpty
    @JsonProperty("qty_received")
    private Integer qtyReceived;
    @NotEmpty
    @JsonProperty("merchandising_department")
    private String merchandisingDepartment;
    @NotEmpty
    @JsonProperty("po_operation_flag")
    private String poOperationFlag;
    @NotEmpty
    @JsonProperty("Change_Order_Description")
    private String changeOrderDescription;
    @NotEmpty
    @JsonProperty("Store_Name")
    private String storeName;
    @NotEmpty
    @JsonProperty("supplier_invoice_date")
    private String supplierInvoiceDate;
    @NotEmpty
    @JsonProperty("Buyer")
    private String buyer;
    @NotEmpty
    @JsonProperty("merchandising_class")
    private String merchandisingClass;
    @NotEmpty
    @JsonProperty("merchandising_sub_class")
    private String merchandisingSubClass;
    @NotEmpty
    @JsonProperty("subclass_merchandiser")
    private String subclassMerchandiser;
    @NotEmpty
    @JsonProperty("created_ts")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.INPUT_DATE_FORMATE)
    private Date createdTs;




}
