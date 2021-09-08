package com.spring.azure.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PoLines {

    @JsonProperty("PO_NBR")
    private String poNbr;
    @JsonProperty("ITEM_FACILITY")
    private String itemFacility;
    @JsonProperty("ITEM_NBR")
    private String itemNbr;
    @JsonProperty("ITEM_PACK")
    private String itemPack;
    @JsonProperty("ITEM_SIZE")
    private String itemSize;
    @JsonProperty("ITEM_DESC")
    private String itemDesc;
    @JsonProperty("ON_ORDER")
    private String onOrder;
    @JsonProperty("LIST_COST")
    private String listCost;
    @JsonProperty("WEIGHT")
    private String weight;
    @JsonProperty("ITEM_CUBE")
    private String itemCube;
    @JsonProperty("PALLET_QUANTITY")
    private String palletQuantity;
    @JsonProperty("LAST_COST")
    private String lastCost;
    @JsonProperty("ITEM_DEPT")
    private String itemDept;
    @JsonProperty("AMT_FREIGHT_BILL")
    private String amtFreightBill;
    @JsonProperty("AMT_BACKHAUL")
    private String amtBackhaul;
    @JsonProperty("CASE_UPC")
    private String caseUpc;
    @JsonProperty("UPC_FORMAT_CD")
    private String upcFormatCd;
    @JsonProperty("WHSE_NO")
    private String whseNo;

}
