package com.sal.prompt.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.List;

@Data
public class POHeaderResponse {
    List<POLine> poLines;


    @CsvBindByPosition(position = 0)
    @JsonProperty("Interface Header Key")
    private String interfaceHeaderKey;

    @CsvBindByPosition(position = 1)
    @JsonProperty("Action")
    private String action;

    @CsvBindByPosition(position = 2)
    @JsonProperty("Batch ID")
    private String batchID;

    @CsvBindByPosition(position = 3)
    @JsonProperty("Import Source")
    private String importSource;

    @CsvBindByPosition(position = 4)
    @JsonProperty("Approval Action")
    private String approvalAction;

    @CsvBindByPosition(position = 5)
    @JsonProperty("Order")
    private String order;

    @CsvBindByPosition(position = 6)
    @JsonProperty("Document Type Code")
    private String documentTypeCode;

    @CsvBindByPosition(position = 7)
    @JsonProperty("Style")
    private String style;

    @CsvBindByPosition(position = 8)
    @JsonProperty("Procurement BU")
    private String procurementBU;

    @CsvBindByPosition(position = 9)
    @JsonProperty("Requisitioning BU")
    private String requisitioningBU;

    @CsvBindByPosition(position = 10)
    @JsonProperty("Sold-to Legal Entity")
    private String soldToLegalEntity;

    @CsvBindByPosition(position = 11)
    @JsonProperty("Bill-to BU")
    private String billToBU;

    @CsvBindByPosition(position = 12)
    @JsonProperty("Buyer")
    private String buyer;

    @CsvBindByPosition(position = 13)
    @JsonProperty("Currency Code")
    private String currencyCode;

    @CsvBindByPosition(position = 14)
    @JsonProperty("Rate")
    private String rate;

    @CsvBindByPosition(position = 15)
    @JsonProperty("Rate Type")
    private String rateType;

    @CsvBindByPosition(position = 16)
    @JsonProperty("Rate Date")
    private String rateDate;

    @CsvBindByPosition(position = 17)
    @JsonProperty("Description")
    private String description;

    @CsvBindByPosition(position = 18)
    @JsonProperty("Bill-to Location")
    private String billToLocation;

    @CsvBindByPosition(position = 19)
    @JsonProperty("Ship-to Location")
    private String shipToLocation;

    @CsvBindByPosition(position = 20)
    @JsonProperty("Supplier")
    private String supplier;

    @CsvBindByPosition(position = 21)
    @JsonProperty("Supplier Number")
    private String supplierNumber;

    @CsvBindByPosition(position = 22)
    @JsonProperty("Supplier Site")
    private String supplierSite;

    @CsvBindByPosition(position = 23)
    @JsonProperty("Supplier Contact")
    private String supplierContact;

    @CsvBindByPosition(position = 24)
    @JsonProperty("Supplier Order")
    private String supplierOrder;

    @CsvBindByPosition(position = 25)
    @JsonProperty("FOB")
    private String fob;

    @CsvBindByPosition(position = 26)
    @JsonProperty("Carrier")
    private String carrier;

    @CsvBindByPosition(position = 27)
    @JsonProperty("Freight Terms")
    private String freightTerms;

    @CsvBindByPosition(position = 28)
    @JsonProperty("Pay On Code")
    private String payOnCode;

    @CsvBindByPosition(position = 29)
    @JsonProperty("Payment Terms")
    private String paymentTerms;

    @CsvBindByPosition(position = 30)
    @JsonProperty("Initiating Party")
    private String initiatingParty;

    @CsvBindByPosition(position = 31)
    @JsonProperty("Change Order Description")
    private String changeOrderDescription;

    @CsvBindByPosition(position = 32)
    @JsonProperty("Required Acknowledgment")
    private String requiredAcknowledgment;

    @CsvBindByPosition(position = 33)
    @JsonProperty("Acknowledge Within (Days)")
    private String acknowledgeWithinDays;

    @CsvBindByPosition(position = 34)
    @JsonProperty("Communication Method")
    private String communicationMethod;

    @CsvBindByPosition(position = 35)
    @JsonProperty("Fax")
    private String fax;

    @CsvBindByPosition(position = 36)
    @JsonProperty("E-mail")
    private String eMail;

    @CsvBindByPosition(position = 37)
    @JsonProperty("Confirming order")
    private String confirmingOrder;

    @CsvBindByPosition(position = 38)
    @JsonProperty("Note to Supplier")
    private String noteToSupplier;

    @CsvBindByPosition(position = 39)
    @JsonProperty("Note to Receiver")
    private String noteToReceiver;

    @CsvBindByPosition(position = 40)
    @JsonProperty("Default Taxation Country Code")
    private String defaultTaxationCountryCode;

    @CsvBindByPosition(position = 41)
    @JsonProperty("Tax Document Subtype Code")
    private String taxDocumentSubtypeCode;

    @CsvBindByPosition(position = 42)
    @JsonProperty("ATTRIBUTE_CATEGORY")
    private String attributeCategory;

    @CsvBindByPosition(position = 43)
    @JsonProperty("ATTRIBUTE1")
    private String attribute1;

    @CsvBindByPosition(position = 44)
    @JsonProperty("ATTRIBUTE2")
    private String attribute2;

    @CsvBindByPosition(position = 45)
    @JsonProperty("ATTRIBUTE3")
    private String attribute3;

    @CsvBindByPosition(position = 46)
    @JsonProperty("ATTRIBUTE4")
    private String attribute4;

    @CsvBindByPosition(position = 47)
    @JsonProperty("ATTRIBUTE5")
    private String attribute5;

    @CsvBindByPosition(position = 48)
    @JsonProperty("ATTRIBUTE6")
    private String attribute6;

    @CsvBindByPosition(position = 49)
    @JsonProperty("ATTRIBUTE7")
    private String attribute7;

    @CsvBindByPosition(position = 50)
    @JsonProperty("ATTRIBUTE8")
    private String attribute8;

    @CsvBindByPosition(position = 51)
    @JsonProperty("ATTRIBUTE9")
    private String attribute9;

    @CsvBindByPosition(position = 52)
    @JsonProperty("ATTRIBUTE10")
    private String attribute10;

    @CsvBindByPosition(position = 53)
    @JsonProperty("ATTRIBUTE11")
    private String attribute11;

    @CsvBindByPosition(position = 54)
    @JsonProperty("ATTRIBUTE12")
    private String attribute12;

    @CsvBindByPosition(position = 55)
    @JsonProperty("ATTRIBUTE13")
    private String attribute13;

    @CsvBindByPosition(position = 56)
    @JsonProperty("ATTRIBUTE14")
    private String attribute14;

    @CsvBindByPosition(position = 57)
    @JsonProperty("ATTRIBUTE15")
    private String attribute15;

    @CsvBindByPosition(position = 58)
    @JsonProperty("ATTRIBUTE16")
    private String attribute16;

    @CsvBindByPosition(position = 59)
    @JsonProperty("ATTRIBUTE17")
    private String attribute17;

    @CsvBindByPosition(position = 60)
    @JsonProperty("ATTRIBUTE18")
    private String attribute18;

    @CsvBindByPosition(position = 61)
    @JsonProperty("ATTRIBUTE19")
    private String attribute19;

    @CsvBindByPosition(position = 62)
    @JsonProperty("ATTRIBUTE20")
    private String attribute20;

    @CsvBindByPosition(position = 63)
    @JsonProperty("ATTRIBUTE_DATE1")
    private String attributeDate1;

    @CsvBindByPosition(position = 64)
    @JsonProperty("ATTRIBUTE_DATE2")
    private String attributeDate2;

    @CsvBindByPosition(position = 65)
    @JsonProperty("ATTRIBUTE_DATE3")
    private String attributeDate3;

    @CsvBindByPosition(position = 66)
    @JsonProperty("ATTRIBUTE_DATE4")
    private String attributeDate4;

    @CsvBindByPosition(position = 67)
    @JsonProperty("ATTRIBUTE_DATE5")
    private String attributeDate5;

    @CsvBindByPosition(position = 68)
    @JsonProperty("ATTRIBUTE_DATE6")
    private String attributeDate6;

    @CsvBindByPosition(position = 69)
    @JsonProperty("ATTRIBUTE_DATE7")
    private String attributeDate7;

    @CsvBindByPosition(position = 70)
    @JsonProperty("ATTRIBUTE_DATE8")
    private String attributeDate8;

    @CsvBindByPosition(position = 71)
    @JsonProperty("ATTRIBUTE_DATE9")
    private String attributeDate9;

    @CsvBindByPosition(position = 72)
    @JsonProperty("ATTRIBUTE_DATE10")
    private String attributeDate10;

    @CsvBindByPosition(position = 73)
    @JsonProperty("ATTRIBUTE_NUMBER1")
    private String attributeNumber1;

    @CsvBindByPosition(position = 74)
    @JsonProperty("ATTRIBUTE_NUMBER2")
    private String attributeNumber2;

    @CsvBindByPosition(position = 75)
    @JsonProperty("ATTRIBUTE_NUMBER3")
    private String attributeNumber3;

    @CsvBindByPosition(position = 76)
    @JsonProperty("ATTRIBUTE_NUMBER4")
    private String attributeNumber4;

    @CsvBindByPosition(position = 77)
    @JsonProperty("ATTRIBUTE_NUMBER5")
    private String attributeNumber5;

    @CsvBindByPosition(position = 78)
    @JsonProperty("ATTRIBUTE_NUMBER6")
    private String attributeNumber6;

    @CsvBindByPosition(position = 79)
    @JsonProperty("ATTRIBUTE_NUMBER7")
    private String attributeNumber7;

    @CsvBindByPosition(position = 80)
    @JsonProperty("ATTRIBUTE_NUMBER8")
    private String attributeNumber8;

    @CsvBindByPosition(position = 81)
    @JsonProperty("ATTRIBUTE_NUMBER9")
    private String attributeNumber9;

    @CsvBindByPosition(position = 82)
    @JsonProperty("ATTRIBUTE_NUMBER10")
    private String attributeNumber10;

    @CsvBindByPosition(position = 83)
    @JsonProperty("ATTRIBUTE_TIMESTAMP1")
    private String attributeTimestamp1;

    @CsvBindByPosition(position = 84)
    @JsonProperty("ATTRIBUTE_TIMESTAMP2")
    private String attributeTimestamp2;

    @CsvBindByPosition(position = 85)
    @JsonProperty("ATTRIBUTE_TIMESTAMP3")
    private String attributeTimestamp3;

    @CsvBindByPosition(position = 86)
    @JsonProperty("ATTRIBUTE_TIMESTAMP4")
    private String attributeTimestamp4;

    @CsvBindByPosition(position = 87)
    @JsonProperty("ATTRIBUTE_TIMESTAMP5")
    private String attributeTimestamp5;

    @CsvBindByPosition(position = 88)
    @JsonProperty("ATTRIBUTE_TIMESTAMP6")
    private String attributeTimestamp6;

    @CsvBindByPosition(position = 89)
    @JsonProperty("ATTRIBUTE_TIMESTAMP7")
    private String attributeTimestamp7;

    @CsvBindByPosition(position = 90)
    @JsonProperty("ATTRIBUTE_TIMESTAMP8")
    private String attributeTimestamp8;

    @CsvBindByPosition(position = 91)
    @JsonProperty("ATTRIBUTE_TIMESTAMP9")
    private String attributeTimestamp9;

    @CsvBindByPosition(position = 92)
    @JsonProperty("ATTRIBUTE_TIMESTAMP10")
    private String attributeTimestamp10;

    @CsvBindByPosition(position = 93)
    @JsonProperty("Buyer E-mail")
    private String buyerEMail;

    @CsvBindByPosition(position = 94)
    @JsonProperty("Mode of Transport")
    private String modeOfTransport;

    @CsvBindByPosition(position = 95)
    @JsonProperty("Service Level")
    private String serviceLevel;

    @CsvBindByPosition(position = 96)
    @JsonProperty("First Party Tax Registration Number")
    private String firstPartyTaxRegistrationNumber;

    @CsvBindByPosition(position = 97)
    @JsonProperty("Third Party Tax Registration Number")
    private String thirdPartyTaxRegistrationNumber;

    @CsvBindByPosition(position = 98)
    @JsonProperty("Buyer Managed Transportation")
    private String buyerManagedTransportation;

    @CsvBindByPosition(position = 99)
    @JsonProperty("Master Contract Number")
    private String masterContractNumber;

    @CsvBindByPosition(position = 100)
    @JsonProperty("Master Contract Type")
    private String masterContractType;
}
