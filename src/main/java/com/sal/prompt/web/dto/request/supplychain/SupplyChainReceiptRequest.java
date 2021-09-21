package com.sal.prompt.web.dto.request.supplychain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sal.prompt.web.dto.request.SourceSystemRequest;
import com.sal.prompt.web.utils.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class SupplyChainReceiptRequest implements SourceSystemRequest {
    @NotEmpty
    @JsonProperty("RECPT_NBR")
    private String recptNbr;
    @NotEmpty
    @JsonProperty("PO_NBR")
    private String poNbr;
    @NotEmpty
    @JsonProperty("VENDOR_NBR")
    private String vendorNbr;
    @NotEmpty
    @JsonProperty("QTY_RECEIVED")
    private Integer qtyReceived;
    @NotEmpty
    @JsonProperty("ITEM_DESC")
    private String itemDesc;
    @NotEmpty
    @JsonProperty("RCV_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.INPUT_DATE_FORMATE)
    private Date rcvDate;
    @NotEmpty
    @JsonProperty("ITEM_FACILITY")
    private String itemFacility;
    @NotEmpty
    @JsonProperty("DEPARTMENT")
    private String department;
    @NotEmpty
    @JsonProperty("DEPART_NAME")
    private String departName;
    @NotEmpty
    @JsonProperty("QTY_ORDERED")
    private Integer qtyOrdered;
    @NotEmpty
    @JsonProperty("ITEM_UPC")
    private Integer itemUpc;
    @NotEmpty
    @JsonProperty("LAST_COST")
    private Integer lastCost;
    @NotEmpty
    @JsonProperty("ITEM_NBR")
    private String itemNbr;
    @NotEmpty
    @JsonProperty("MERCH_NBR")
    private String merchNbr;
    @NotEmpty
    @JsonProperty("OFF_INVOICE")
    private String offInvoice;
    @NotEmpty
    @JsonProperty("HI_STORE_PACK")
    private String hiStorePack;
    @NotEmpty
    @JsonProperty("HI_ITEM_SIZE")
    private String hiItemSize;
    @NotEmpty
    @JsonProperty("ITEM_LST_COST")
    private String itemLstCost;
    @NotEmpty
    @JsonProperty("BUYER_NBR")
    private String buyerNbr;

}
