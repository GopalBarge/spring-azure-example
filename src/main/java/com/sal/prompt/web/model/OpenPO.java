package com.sal.prompt.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenPO {
    @JsonProperty("DOCUMENT_NUMBER")
    public String documentNumber;
    @JsonProperty("PO_NBR")
    public String poNbr;
    @JsonProperty("VENDOR_NBR")
    public String vendorNbr;
    @JsonProperty("VENDOR_FAC")
    public String vendorFac;
    @JsonProperty("LINE_NUM")
    public String lineNum;
    @JsonProperty("ITEM_FACILITY")
    public String itemFacility;
    @JsonProperty("ITEM_NBR")
    public String itemNbr;
    @JsonProperty("ITEM_UPC")
    public String itemUpc;
    @JsonProperty("RECEIPT_ID")
    public String receiptId;
    @JsonProperty("UNIT_PRICE")
    public String unitPrice;
    @JsonProperty("UOM_CODE")
    public String uomCode;
    @JsonProperty("PO_HEADER_ID")
    public String poHeaderId;
/*
{
   "DOCUMENT_NUMBER": "SAL503932",
   "CREATED_BY_USERID": "123456",
   "RECEIPT_ID": "67890",
   "STORE_ID": "13579",
   "LINE_NUM": "1",
   "QUANTITY": "1190",
   "UNIT_PRICE": "11.6",
   "UOM_CODE": "zzv",
   "LINE_STORE_ID": "912",
   "STORE_NAME": "10090",
   "SUPPLIER_INVOICE_NO": "912141262",
   "RECEIPT_ITEM_ID": "85",
   "UPC_CODE": "16 OZ",
   "RETAIL_DEPARTMENT": "27.15",
   "RETAIL_CLASS": "0.57",
   "ITEM_SIZE": "1",
   "UOM_DESCRIPTOR": "1",
   "RETAIL_QTY": "N",
   "RETAIL_PRICE": "7572510090"
}
*/

}

