package com.spring.azure.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class POHeader {
    @JsonProperty("VENDOR_NBR")
    private String vendorNbr;
    @JsonProperty("VENDOR_FAC")
    private String vendorFac;
    @JsonProperty("VEND_FAC_NAME")
    private String vendFacName;
    @JsonProperty("BUYER_NAME")
    private String buyerName;
    @JsonProperty("FREIGHT_ALLOW")
    private String freightAllow;
    @JsonProperty("TERMS_PERCENT")
    private String termsPercent;
    @JsonProperty("TERMS_DAYS")
    private String termsDays;
    @JsonProperty("TERMS_NET_DAYS")
    private String termsNetDays;
    @JsonProperty("DATE_PICKUP")
    private String datePickup;
    @JsonProperty("DATE_ORDERED")
    private String dateOrdered;
    @JsonProperty("DATE_RECEIVED")
    private String dateReceived;
    @JsonProperty("BACKHAUL")
    private String backhaul;
    @JsonProperty("FLAG_BACKHAUL")
    private String flagBackhaul;
    @JsonProperty("FLAG_TRUCK")
    private String flagTruck;
    @JsonProperty("FREIGHT_BILL_FLAG")
    private String freightBillFlag;
    @JsonProperty("ORDERED_WEIGHT")
    private String orderedWeight;
    @JsonProperty("ORDERED_QTY")
    private String orderedQty;
    @JsonProperty("WHSE_NO")
    private String whseNo;
    @JsonProperty("OPERATION_FLAG")
    private String operationFlag;

}
