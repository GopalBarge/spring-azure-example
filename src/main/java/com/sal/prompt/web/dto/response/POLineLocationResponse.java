package com.sal.prompt.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.List;

@Data
public class POLineLocationResponse {
    List<PODistributionResponse> poDistributionResponses;


    @CsvBindByPosition(position = 0)
    @JsonProperty("Interface Line Location Key")
    private String interfaceLineLocationKey;

    @CsvBindByPosition(position = 1)
    @JsonProperty("Interface Line Key")
    private String interfaceLineKey;

    @CsvBindByPosition(position = 2)
    @JsonProperty("Schedule")
    private String schedule;

    @CsvBindByPosition(position = 3)
    @JsonProperty("Ship-to Location")
    private String shipToLocation;

    @CsvBindByPosition(position = 4)
    @JsonProperty("Ship-to Organization")
    private String shipToOrganization;

    @CsvBindByPosition(position = 5)
    @JsonProperty("Amount")
    private String amount;

    @CsvBindByPosition(position = 6)
    @JsonProperty("Quantity")
    private String quantity;

    @CsvBindByPosition(position = 7)
    @JsonProperty("Need-by Date")
    private String needByDate;

    @CsvBindByPosition(position = 8)
    @JsonProperty("Promised Date")
    private String promisedDate;

    @CsvBindByPosition(position = 9)
    @JsonProperty("Secondary Quantity")
    private String secondaryQuantity;

    @CsvBindByPosition(position = 10)
    @JsonProperty("Secondary UOM")
    private String secondaryUOM;

    @CsvBindByPosition(position = 11)
    @JsonProperty("Destination Type Code")
    private String destinationTypeCode;

    @CsvBindByPosition(position = 12)
    @JsonProperty("Accrue at receipt")
    private String accrueAtReceipt;

    @CsvBindByPosition(position = 13)
    @JsonProperty("Allow substitute receipts")
    private String allowSubstituteReceipts;

    @CsvBindByPosition(position = 14)
    @JsonProperty("Assessable Value")
    private String assessableValue;

    @CsvBindByPosition(position = 15)
    @JsonProperty("Early Receipt Tolerance in Days")
    private String earlyReceiptToleranceInDays;

    @CsvBindByPosition(position = 16)
    @JsonProperty("Late Receipt Tolerance in Days")
    private String lateReceiptToleranceInDays;

    @CsvBindByPosition(position = 17)
    @JsonProperty("Enforce Ship to Location Code")
    private String enforceShipToLocationCode;

    @CsvBindByPosition(position = 18)
    @JsonProperty("Inspection Required")
    private String inspectionRequired;

    @CsvBindByPosition(position = 19)
    @JsonProperty("Receipt Required")
    private String receiptRequired;

    @CsvBindByPosition(position = 20)
    @JsonProperty("Invoice Close Tolerance Percent")
    private String invoiceCloseTolerancePercent;

    @CsvBindByPosition(position = 21)
    @JsonProperty("Receipt Close Tolerance Percent")
    private String receiptCloseTolerancePercent;

    @CsvBindByPosition(position = 22)
    @JsonProperty("Qty Rcv Tolerance")
    private String qtyRcvTolerance;

    @CsvBindByPosition(position = 23)
    @JsonProperty("Qty Rcv Exception Code")
    private String qtyRcvExceptionCode;

    @CsvBindByPosition(position = 24)
    @JsonProperty("Receipt Days Exception Code")
    private String receiptDaysExceptionCode;

    @CsvBindByPosition(position = 25)
    @JsonProperty("Receipt Routing")
    private String receiptRouting;

    @CsvBindByPosition(position = 26)
    @JsonProperty("Note to receiver")
    private String noteToReceiver;

    @CsvBindByPosition(position = 27)
    @JsonProperty("Tax Classification Code")
    private String taxClassificationCode;

    @CsvBindByPosition(position = 28)
    @JsonProperty("Intended Use")
    private String intendedUse;

    @CsvBindByPosition(position = 29)
    @JsonProperty("Product Category Code")
    private String productCategoryCode;

    @CsvBindByPosition(position = 30)
    @JsonProperty("Product Fiscal Classification")
    private String productFiscalClassification;

    @CsvBindByPosition(position = 31)
    @JsonProperty("Product Type Code")
    private String productTypeCode;

    @CsvBindByPosition(position = 32)
    @JsonProperty("Transaction Business Category Code")
    private String transactionBusinessCategoryCode;

    @CsvBindByPosition(position = 33)
    @JsonProperty("User-Defined Fiscal Classification Code")
    private String userDefinedFiscalClassificationCode;

    @CsvBindByPosition(position = 34)
    @JsonProperty("ATTRIBUTE_CATEGORY")
    private String attributeCategory;

    @CsvBindByPosition(position = 35)
    @JsonProperty("ATTRIBUTE1")
    private String attribute1;

    @CsvBindByPosition(position = 36)
    @JsonProperty("ATTRIBUTE2")
    private String attribute2;

    @CsvBindByPosition(position = 37)
    @JsonProperty("ATTRIBUTE3")
    private String attribute3;

    @CsvBindByPosition(position = 38)
    @JsonProperty("ATTRIBUTE4")
    private String attribute4;

    @CsvBindByPosition(position = 39)
    @JsonProperty("ATTRIBUTE5")
    private String attribute5;

    @CsvBindByPosition(position = 40)
    @JsonProperty("ATTRIBUTE6")
    private String attribute6;

    @CsvBindByPosition(position = 41)
    @JsonProperty("ATTRIBUTE7")
    private String attribute7;

    @CsvBindByPosition(position = 42)
    @JsonProperty("ATTRIBUTE8")
    private String attribute8;

    @CsvBindByPosition(position = 43)
    @JsonProperty("ATTRIBUTE9")
    private String attribute9;

    @CsvBindByPosition(position = 44)
    @JsonProperty("ATTRIBUTE10")
    private String attribute10;

    @CsvBindByPosition(position = 45)
    @JsonProperty("ATTRIBUTE11")
    private String attribute11;

    @CsvBindByPosition(position = 46)
    @JsonProperty("ATTRIBUTE12")
    private String attribute12;

    @CsvBindByPosition(position = 47)
    @JsonProperty("ATTRIBUTE13")
    private String attribute13;

    @CsvBindByPosition(position = 48)
    @JsonProperty("ATTRIBUTE14")
    private String attribute14;

    @CsvBindByPosition(position = 49)
    @JsonProperty("ATTRIBUTE15")
    private String attribute15;

    @CsvBindByPosition(position = 50)
    @JsonProperty("ATTRIBUTE16")
    private String attribute16;

    @CsvBindByPosition(position = 51)
    @JsonProperty("ATTRIBUTE17")
    private String attribute17;

    @CsvBindByPosition(position = 52)
    @JsonProperty("ATTRIBUTE18")
    private String attribute18;

    @CsvBindByPosition(position = 53)
    @JsonProperty("ATTRIBUTE19")
    private String attribute19;

    @CsvBindByPosition(position = 54)
    @JsonProperty("ATTRIBUTE20")
    private String attribute20;

    @CsvBindByPosition(position = 55)
    @JsonProperty("ATTRIBUTE_DATE1")
    private String attributeDate1;

    @CsvBindByPosition(position = 56)
    @JsonProperty("ATTRIBUTE_DATE2")
    private String attributeDate2;

    @CsvBindByPosition(position = 57)
    @JsonProperty("ATTRIBUTE_DATE3")
    private String attributeDate3;

    @CsvBindByPosition(position = 58)
    @JsonProperty("ATTRIBUTE_DATE4")
    private String attributeDate4;

    @CsvBindByPosition(position = 59)
    @JsonProperty("ATTRIBUTE_DATE5")
    private String attributeDate5;

    @CsvBindByPosition(position = 60)
    @JsonProperty("ATTRIBUTE_DATE6")
    private String attributeDate6;

    @CsvBindByPosition(position = 61)
    @JsonProperty("ATTRIBUTE_DATE7")
    private String attributeDate7;

    @CsvBindByPosition(position = 62)
    @JsonProperty("ATTRIBUTE_DATE8")
    private String attributeDate8;

    @CsvBindByPosition(position = 63)
    @JsonProperty("ATTRIBUTE_DATE9")
    private String attributeDate9;

    @CsvBindByPosition(position = 64)
    @JsonProperty("ATTRIBUTE_DATE10")
    private String attributeDate10;

    @CsvBindByPosition(position = 65)
    @JsonProperty("ATTRIBUTE_NUMBER1")
    private String attributeNumber1;

    @CsvBindByPosition(position = 66)
    @JsonProperty("ATTRIBUTE_NUMBER2")
    private String attributeNumber2;

    @CsvBindByPosition(position = 67)
    @JsonProperty("ATTRIBUTE_NUMBER3")
    private String attributeNumber3;

    @CsvBindByPosition(position = 68)
    @JsonProperty("ATTRIBUTE_NUMBER4")
    private String attributeNumber4;

    @CsvBindByPosition(position = 69)
    @JsonProperty("ATTRIBUTE_NUMBER5")
    private String attributeNumber5;

    @CsvBindByPosition(position = 70)
    @JsonProperty("ATTRIBUTE_NUMBER6")
    private String attributeNumber6;

    @CsvBindByPosition(position = 71)
    @JsonProperty("ATTRIBUTE_NUMBER7")
    private String attributeNumber7;

    @CsvBindByPosition(position = 72)
    @JsonProperty("ATTRIBUTE_NUMBER8")
    private String attributeNumber8;

    @CsvBindByPosition(position = 73)
    @JsonProperty("ATTRIBUTE_NUMBER9")
    private String attributeNumber9;

    @CsvBindByPosition(position = 74)
    @JsonProperty("ATTRIBUTE_NUMBER10")
    private String attributeNumber10;

    @CsvBindByPosition(position = 75)
    @JsonProperty("ATTRIBUTE_TIMESTAMP1")
    private String attributeTimestamp1;

    @CsvBindByPosition(position = 76)
    @JsonProperty("ATTRIBUTE_TIMESTAMP2")
    private String attributeTimestamp2;

    @CsvBindByPosition(position = 77)
    @JsonProperty("ATTRIBUTE_TIMESTAMP3")
    private String attributeTimestamp3;

    @CsvBindByPosition(position = 78)
    @JsonProperty("ATTRIBUTE_TIMESTAMP4")
    private String attributeTimestamp4;

    @CsvBindByPosition(position = 79)
    @JsonProperty("ATTRIBUTE_TIMESTAMP5")
    private String attributeTimestamp5;

    @CsvBindByPosition(position = 80)
    @JsonProperty("ATTRIBUTE_TIMESTAMP6")
    private String attributeTimestamp6;

    @CsvBindByPosition(position = 81)
    @JsonProperty("ATTRIBUTE_TIMESTAMP7")
    private String attributeTimestamp7;

    @CsvBindByPosition(position = 82)
    @JsonProperty("ATTRIBUTE_TIMESTAMP8")
    private String attributeTimestamp8;

    @CsvBindByPosition(position = 83)
    @JsonProperty("ATTRIBUTE_TIMESTAMP9")
    private String attributeTimestamp9;

    @CsvBindByPosition(position = 84)
    @JsonProperty("ATTRIBUTE_TIMESTAMP10")
    private String attributeTimestamp10;

    @CsvBindByPosition(position = 85)
    @JsonProperty("Carrier")
    private String carrier;

    @CsvBindByPosition(position = 86)
    @JsonProperty("Mode of Transport")
    private String modeOfTransport;

    @CsvBindByPosition(position = 87)
    @JsonProperty("Service Level")
    private String serviceLevel;

    @CsvBindByPosition(position = 88)
    @JsonProperty("Final discharge location code")
    private String finalDischargeLocationCode;

    @CsvBindByPosition(position = 89)
    @JsonProperty("Requested Ship Date")
    private String requestedShipDate;

    @CsvBindByPosition(position = 90)
    @JsonProperty("Promised Ship Date")
    private String promisedShipDate;

    @CsvBindByPosition(position = 91)
    @JsonProperty("Requested Delivery Date")
    private String requestedDeliveryDate;

    @CsvBindByPosition(position = 92)
    @JsonProperty("Promised Delivery Date")
    private String promisedDeliveryDate;

    @CsvBindByPosition(position = 93)
    @JsonProperty("Retainage Rate (%)")
    private String retainageRate;

}
