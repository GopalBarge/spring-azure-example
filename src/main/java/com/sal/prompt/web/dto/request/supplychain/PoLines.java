package com.sal.prompt.web.dto.request.supplychain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sal.prompt.web.utils.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class PoLines {
    @NotEmpty
    @JsonProperty("PO_NBR")
    private String poNbr;
    @NotEmpty
    @JsonProperty("ITEM_FACILITY")
    private String itemFacility;
    @NotEmpty
    @JsonProperty("ITEM_NBR")
    private String itemNbr;
    @NotEmpty
    @JsonProperty("ITEM_PACK")
    private String itemPack;
    @NotEmpty
    @JsonProperty("ITEM_SIZE")
    private String itemSize;
    @NotEmpty
    @JsonProperty("ITEM_DESC")
    private String itemDesc;
    @NotEmpty
    @JsonProperty("ON_ORDER")
    private Integer onOrder;
    @NotEmpty
    @JsonProperty("LIST_COST")
    private Integer listCost;
    @NotEmpty
    @JsonProperty("WEIGHT")
    private Integer weight;
    @NotEmpty
    @JsonProperty("ITEM_CUBE")
    private String itemCube;
    @NotEmpty
    @JsonProperty("PALLET_QUANTITY")
    private Integer palletQuantity;
    @NotEmpty
    @JsonProperty("LAST_COST")
    private Integer lastCost;
    @NotEmpty
    @JsonProperty("ITEM_DEPT")
    private String itemDept;
    @NotEmpty
    @JsonProperty("AMT_FREIGHT_BILL")
    private String amtFreightBill;
    @NotEmpty
    @JsonProperty("AMT_BACKHAUL")
    private String amtBackhaul;
    @NotEmpty
    @JsonProperty("CASE_UPC")
    private String caseUpc;
    @NotEmpty
    @JsonProperty("UPC_FORMAT_CD")
    private String upcFormatCd;
    @NotEmpty
    @JsonProperty("WHSE_NO")
    private Integer whseNo;
    @NotEmpty
    @JsonProperty("DUE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.INPUT_DATE_FORMATE)
    private Date dueDate;
}
