package com.spring.azure.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.azure.demo.utils.Constants;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class POHeader {

    @NotEmpty
    @JsonProperty("BUYER")
    private String buyer;
    @NotEmpty
    @JsonProperty("PO_NBR")
    private String poNbr;
    @NotEmpty
    @JsonProperty("VENDOR_NBR")
    private String vendorNbr;
    @NotEmpty
    @JsonProperty("VENDOR_FAC")
    private String vendorFac;
    @NotEmpty
    @JsonProperty("VEND_FAC_NAME")
    private String vendFacName;
    @NotEmpty
    @JsonProperty("BUYER_NAME")
    private String buyerName;
    @NotEmpty
    @JsonProperty("FREIGHT_ALLOW")
    private String freightAllow;
    @NotEmpty
    @JsonProperty("TERMS_PERCENT")
    private String termsPercent;
    @NotEmpty
    @JsonProperty("TERMS_DAYS")
    private String termsDays;
    @NotEmpty
    @JsonProperty("TERMS_NET_DAYS")
    private String termsNetDays;
    @NotEmpty
    @JsonProperty("DATE_PICKUP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.INPUT_DATE_FORMATE)
    private Date datePickup;
    @NotEmpty
    @JsonProperty("DATE_ORDERED")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.INPUT_DATE_FORMATE)
    private Date dateOrdered;
    @NotEmpty
    @JsonProperty("DATE_RECEIVED")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.INPUT_DATE_FORMATE)
    private Date dateReceived;
    @NotEmpty
    @JsonProperty("BACKHAUL")
    private String backhaul;
    @NotEmpty
    @JsonProperty("FLAG_BACKHAUL")
    private String flagBackhaul;
    @NotEmpty
    @JsonProperty("FLAG_TRUCK")
    private String flagTruck;
    @NotEmpty
    @JsonProperty("FREIGHT_BILL_FLAG")
    private String freightBillFlag;
    @NotEmpty
    @JsonProperty("ORDERED_WEIGHT")
    private Integer orderedWeight;
    @NotEmpty
    @JsonProperty("ORDERED_QTY")
    private Integer orderedQty;
    @NotEmpty
    @JsonProperty("WHSE_NO")
    private Integer whseNo;
    @NotEmpty
    @JsonProperty("OPERATION_FLAG")
    private String operationFlag;

}
