package com.sal.prompt.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.List;

@Data
public class ReceiptHeaderResponse implements TargetSystemResponse{

    List<ReceiptLineResponse> receiptLineResponses;

    @CsvBindByPosition(position = 0)
    @JsonProperty("Header Interface Number")
    private String headerInterfaceNumber;

    @CsvBindByPosition(position = 1)
    @JsonProperty("Receipt Source Code")
    private String receiptSourceCode;

    @CsvBindByPosition(position = 2)
    @JsonProperty("ASN Type")
    private String aSNType;

    @CsvBindByPosition(position = 3)
    @JsonProperty("Transaction Type")
    private String transactionType;

    @CsvBindByPosition(position = 4)
    @JsonProperty("Notice Creation Date")
    private String noticeCreationDate;

    @CsvBindByPosition(position = 5)
    @JsonProperty("Shipment Number")
    private String shipmentNumber;

    @CsvBindByPosition(position = 6)
    @JsonProperty("Receipt Number")
    private String receiptNumber;

    @CsvBindByPosition(position = 7)
    @JsonProperty("Supplier Name")
    private String supplierName;

    @CsvBindByPosition(position = 8)
    @JsonProperty("Supplier Number")
    private String supplierNumber;

    @CsvBindByPosition(position = 9)
    @JsonProperty("Supplier Site Code")
    private String supplierSiteCode;

    @CsvBindByPosition(position = 10)
    @JsonProperty("From Organization Code")
    private String fromOrganizationCode;

    @CsvBindByPosition(position = 11)
    @JsonProperty("Ship-to Organization Code")
    private String shipToOrganizationCode;

    @CsvBindByPosition(position = 12)
    @JsonProperty("Location Code")
    private String locationCode;

    @CsvBindByPosition(position = 13)
    @JsonProperty("Bill of Lading")
    private String billOfLading;

    @CsvBindByPosition(position = 14)
    @JsonProperty("Packing Slip")
    private String packingSlip;

    @CsvBindByPosition(position = 15)
    @JsonProperty("Shipped Date")
    private String shippedDate;

    @CsvBindByPosition(position = 16)
    @JsonProperty("Carrier Name")
    private String carrierName;

    @CsvBindByPosition(position = 17)
    @JsonProperty("Expected Receipt Date")
    private String expectedReceiptDate;

    @CsvBindByPosition(position = 18)
    @JsonProperty("Number of Containers")
    private String numberOfContainers;

    @CsvBindByPosition(position = 19)
    @JsonProperty("Waybill")
    private String waybill;

    @CsvBindByPosition(position = 20)
    @JsonProperty("Comments")
    private String comments;

    @CsvBindByPosition(position = 21)
    @JsonProperty("Gross Weight")
    private String grossWeight;

    @CsvBindByPosition(position = 22)
    @JsonProperty("Gross Weight UOM")
    private String grossWeightUOM;

    @CsvBindByPosition(position = 23)
    @JsonProperty("Net Weight")
    private String netWeight;

    @CsvBindByPosition(position = 24)
    @JsonProperty("Net Weight UOM")
    private String netWeightUOM;

    @CsvBindByPosition(position = 25)
    @JsonProperty("Tare Weight")
    private String tareWeight;

    @CsvBindByPosition(position = 26)
    @JsonProperty("Tare Weight UOM")
    private String tareWeightUOM;

    @CsvBindByPosition(position = 27)
    @JsonProperty("Packaging Code")
    private String packagingCode;

    @CsvBindByPosition(position = 28)
    @JsonProperty("Carrier Method")
    private String carrierMethod;

    @CsvBindByPosition(position = 29)
    @JsonProperty("Carrier Equipment")
    private String carrierEquipment;

    @CsvBindByPosition(position = 30)
    @JsonProperty("Special Handling Code")
    private String specialHandlingCode;

    @CsvBindByPosition(position = 31)
    @JsonProperty("Hazard Code")
    private String hazardCode;

    @CsvBindByPosition(position = 32)
    @JsonProperty("Hazard Class")
    private String hazardClass;

    @CsvBindByPosition(position = 33)
    @JsonProperty("Hazard Description")
    private String hazardDescription;

    @CsvBindByPosition(position = 34)
    @JsonProperty("Freight Terms")
    private String freightTerms;

    @CsvBindByPosition(position = 35)
    @JsonProperty("Freight Bill Number")
    private String freightBillNumber;

    @CsvBindByPosition(position = 36)
    @JsonProperty("Invoice Number")
    private String invoiceNumber;

    @CsvBindByPosition(position = 37)
    @JsonProperty("Invoice Date")
    private String invoiceDate;

    @CsvBindByPosition(position = 38)
    @JsonProperty("Total Invoice Amount")
    private String totalInvoiceAmount;

    @CsvBindByPosition(position = 39)
    @JsonProperty("Tax Name")
    private String taxName;

    @CsvBindByPosition(position = 40)
    @JsonProperty("Tax Amount")
    private String taxAmount;

    @CsvBindByPosition(position = 41)
    @JsonProperty("Freight Amount")
    private String freightAmount;

    @CsvBindByPosition(position = 42)
    @JsonProperty("Currency Code")
    private String currencyCode;

    @CsvBindByPosition(position = 43)
    @JsonProperty("Currency Conversion Rate Type")
    private String currencyConversionRateType;

    @CsvBindByPosition(position = 44)
    @JsonProperty("Currency Conversion Rate")
    private String currencyConversionRate;

    @CsvBindByPosition(position = 45)
    @JsonProperty("Currency Conversion Rate Date")
    private String currencyConversionRateDate;

    @CsvBindByPosition(position = 46)
    @JsonProperty("Payment Terms Name")
    private String paymentTermsName;

    @CsvBindByPosition(position = 47)
    @JsonProperty("Employee Name")
    private String employeeName;

    @CsvBindByPosition(position = 48)
    @JsonProperty("Transaction Date")
    private String transactionDate;

    @CsvBindByPosition(position = 49)
    @JsonProperty("Customer Account Number")
    private String customerAccountNumber;

    @CsvBindByPosition(position = 50)
    @JsonProperty("Customer Name")
    private String customerName;

    @CsvBindByPosition(position = 51)
    @JsonProperty("Customer Number")
    private String customerNumber;

    @CsvBindByPosition(position = 52)
    @JsonProperty("Business Unit")
    private String businessUnit;

    @CsvBindByPosition(position = 53)
    @JsonProperty("Logistics Outsourcer Customer Party Name")
    private String logisticsOutsourcerCustomerPartyName;

    @CsvBindByPosition(position = 54)
    @JsonProperty("Receipt Advice Number")
    private String receiptAdviceNumber;

    @CsvBindByPosition(position = 55)
    @JsonProperty("Receipt Advice Document Code")
    private String receiptAdviceDocumentCode;

    @CsvBindByPosition(position = 56)
    @JsonProperty("Receipt Advice Document Number")
    private String receiptAdviceDocumentNumber;

    @CsvBindByPosition(position = 57)
    @JsonProperty("Receipt Advice Document Revision")
    private String receiptAdviceDocumentRevision;

    @CsvBindByPosition(position = 58)
    @JsonProperty("Receipt Advice Document Revision Date")
    private String receiptAdviceDocumentRevisionDate;

    @CsvBindByPosition(position = 59)
    @JsonProperty("Receipt Advice Document Creation Date")
    private String receiptAdviceDocumentCreationDate;

    @CsvBindByPosition(position = 60)
    @JsonProperty("Receipt Advice Document Last Update Date")
    private String receiptAdviceDocumentLastUpdateDate;

    @CsvBindByPosition(position = 61)
    @JsonProperty("Receipt Advice Outsourcer Contact Name")
    private String receiptAdviceOutsourcerContactName;

    @CsvBindByPosition(position = 62)
    @JsonProperty("Receipt Advice Supplier Site Name")
    private String receiptAdviceSupplierSiteName;

    @CsvBindByPosition(position = 63)
    @JsonProperty("Receipt Advice Notes to Receiver")
    private String receiptAdviceNotesToReceiver;

    @CsvBindByPosition(position = 64)
    @JsonProperty("Receipt Advice Source System Name")
    private String receiptAdviceSourceSystemName;

    @CsvBindByPosition(position = 65)
    @JsonProperty("Attribute Category")
    private String attributeCategory;

    @CsvBindByPosition(position = 66)
    @JsonProperty("ATTRIBUTE1")
    private String attribute1;

    @CsvBindByPosition(position = 67)
    @JsonProperty("ATTRIBUTE2")
    private String attribute2;

    @CsvBindByPosition(position = 68)
    @JsonProperty("ATTRIBUTE3")
    private String attribute3;

    @CsvBindByPosition(position = 69)
    @JsonProperty("ATTRIBUTE4")
    private String attribute4;

    @CsvBindByPosition(position = 70)
    @JsonProperty("ATTRIBUTE5")
    private String attribute5;

    @CsvBindByPosition(position = 71)
    @JsonProperty("ATTRIBUTE6")
    private String attribute6;

    @CsvBindByPosition(position = 72)
    @JsonProperty("ATTRIBUTE7")
    private String attribute7;

    @CsvBindByPosition(position = 73)
    @JsonProperty("ATTRIBUTE8")
    private String attribute8;

    @CsvBindByPosition(position = 74)
    @JsonProperty("ATTRIBUTE9")
    private String attribute9;

    @CsvBindByPosition(position = 75)
    @JsonProperty("ATTRIBUTE10")
    private String attribute10;

    @CsvBindByPosition(position = 76)
    @JsonProperty("ATTRIBUTE11")
    private String attribute11;

    @CsvBindByPosition(position = 77)
    @JsonProperty("ATTRIBUTE12")
    private String attribute12;

    @CsvBindByPosition(position = 78)
    @JsonProperty("ATTRIBUTE13")
    private String attribute13;

    @CsvBindByPosition(position = 79)
    @JsonProperty("ATTRIBUTE14")
    private String attribute14;

    @CsvBindByPosition(position = 80)
    @JsonProperty("ATTRIBUTE15")
    private String attribute15;

    @CsvBindByPosition(position = 81)
    @JsonProperty("ATTRIBUTE16")
    private String attribute16;

    @CsvBindByPosition(position = 82)
    @JsonProperty("ATTRIBUTE17")
    private String attribute17;

    @CsvBindByPosition(position = 83)
    @JsonProperty("ATTRIBUTE18")
    private String attribute18;

    @CsvBindByPosition(position = 84)
    @JsonProperty("ATTRIBUTE19")
    private String attribute19;

    @CsvBindByPosition(position = 85)
    @JsonProperty("ATTRIBUTE20")
    private String attribute20;

    @CsvBindByPosition(position = 86)
    @JsonProperty("ATTRIBUTE_NUMBER1")
    private String attributeNumber1;

    @CsvBindByPosition(position = 87)
    @JsonProperty("ATTRIBUTE_NUMBER2")
    private String attributeNumber2;

    @CsvBindByPosition(position = 88)
    @JsonProperty("ATTRIBUTE_NUMBER3")
    private String attributeNumber3;

    @CsvBindByPosition(position = 89)
    @JsonProperty("ATTRIBUTE_NUMBER4")
    private String attributeNumber4;

    @CsvBindByPosition(position = 90)
    @JsonProperty("ATTRIBUTE_NUMBER5")
    private String attributeNumber5;

    @CsvBindByPosition(position = 91)
    @JsonProperty("ATTRIBUTE_NUMBER6")
    private String attributeNumber6;

    @CsvBindByPosition(position = 92)
    @JsonProperty("ATTRIBUTE_NUMBER7")
    private String attributeNumber7;

    @CsvBindByPosition(position = 93)
    @JsonProperty("ATTRIBUTE_NUMBER8")
    private String attributeNumber8;

    @CsvBindByPosition(position = 94)
    @JsonProperty("ATTRIBUTE_NUMBER9")
    private String attributeNumber9;

    @CsvBindByPosition(position = 95)
    @JsonProperty("ATTRIBUTE_NUMBER10")
    private String attributeNumber10;

    @CsvBindByPosition(position = 96)
    @JsonProperty("ATTRIBUTE_DATE1")
    private String attributeDate1;

    @CsvBindByPosition(position = 97)
    @JsonProperty("ATTRIBUTE_DATE2")
    private String attributeDate2;

    @CsvBindByPosition(position = 98)
    @JsonProperty("ATTRIBUTE_DATE3")
    private String attributeDate3;

    @CsvBindByPosition(position = 99)
    @JsonProperty("ATTRIBUTE_DATE4")
    private String attributeDate4;

    @CsvBindByPosition(position = 100)
    @JsonProperty("ATTRIBUTE_DATE5")
    private String attributeDate5;

    @CsvBindByPosition(position = 101)
    @JsonProperty("ATTRIBUTE_TIMESTAMP1")
    private String attributeTimestamp1;

    @CsvBindByPosition(position = 102)
    @JsonProperty("ATTRIBUTE_TIMESTAMP2")
    private String attributeTimestamp2;

    @CsvBindByPosition(position = 103)
    @JsonProperty("ATTRIBUTE_TIMESTAMP3")
    private String attributeTimestamp3;

    @CsvBindByPosition(position = 104)
    @JsonProperty("ATTRIBUTE_TIMESTAMP4")
    private String attributeTimestamp4;

    @CsvBindByPosition(position = 105)
    @JsonProperty("ATTRIBUTE_TIMESTAMP5")
    private String attributeTimestamp5;

    @CsvBindByPosition(position = 106)
    @JsonProperty("GL Date")
    private String gLDate;

    @CsvBindByPosition(position = 107)
    @JsonProperty("Receipt Header Id")
    private String receiptHeaderId;

    @CsvBindByPosition(position = 108)
    @JsonProperty("External System Transaction Reference")
    private String externalSystemTransactionReference;


}
