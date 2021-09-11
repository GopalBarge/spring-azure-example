package com.sal.prompt.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.List;
@Data
public class POLineResponse {
    List<POLineLocationResponse> poLineLocationResponses;


    @CsvBindByPosition(position = 0)
    @JsonProperty("Interface Line Key")
    private String interfaceLineKey;

    @CsvBindByPosition(position = 1)
    @JsonProperty("Interface Header Key")
    private String interfaceHeaderKey;

    @CsvBindByPosition(position = 2)
    @JsonProperty("Action")
    private String action;

    @CsvBindByPosition(position = 3)
    @JsonProperty("Line")
    private String line;

    @CsvBindByPosition(position = 4)
    @JsonProperty("Line Type")
    private String lineType;

    @CsvBindByPosition(position = 5)
    @JsonProperty("Item")
    private String item;

    @CsvBindByPosition(position = 6)
    @JsonProperty("Item Description")
    private String itemDescription;

    @CsvBindByPosition(position = 7)
    @JsonProperty("Item Revision")
    private String itemRevision;

    @CsvBindByPosition(position = 8)
    @JsonProperty("Category Name")
    private String categoryName;

    @CsvBindByPosition(position = 9)
    @JsonProperty("Amount")
    private String amount;

    @CsvBindByPosition(position = 10)
    @JsonProperty("Quantity")
    private String quantity;

    @CsvBindByPosition(position = 11)
    @JsonProperty("UOM")
    private String uom;

    @CsvBindByPosition(position = 12)
    @JsonProperty("Price")
    private String price;

    @CsvBindByPosition(position = 13)
    @JsonProperty("Secondary Quantity")
    private String secondaryQuantity;

    @CsvBindByPosition(position = 14)
    @JsonProperty("Secondary UOM")
    private String secondaryUOM;

    @CsvBindByPosition(position = 15)
    @JsonProperty("Supplier Item")
    private String supplierItem;

    @CsvBindByPosition(position = 16)
    @JsonProperty("Negotiated")
    private String negotiated;

    @CsvBindByPosition(position = 17)
    @JsonProperty("Hazard Class")
    private String hazardClass;

    @CsvBindByPosition(position = 18)
    @JsonProperty("UN Number")
    private String uNNumber;

    @CsvBindByPosition(position = 19)
    @JsonProperty("Note to Supplier")
    private String noteToSupplier;

    @CsvBindByPosition(position = 20)
    @JsonProperty("Note to Receiver")
    private String noteToReceiver;

    @CsvBindByPosition(position = 21)
    @JsonProperty("ATTRIBUTE_CATEGORY")
    private String attributeCategory;

    @CsvBindByPosition(position = 22)
    @JsonProperty("ATTRIBUTE1")
    private String attribute1;

    @CsvBindByPosition(position = 23)
    @JsonProperty("ATTRIBUTE2")
    private String attribute2;

    @CsvBindByPosition(position = 24)
    @JsonProperty("ATTRIBUTE3")
    private String attribute3;

    @CsvBindByPosition(position = 25)
    @JsonProperty("ATTRIBUTE4")
    private String attribute4;

    @CsvBindByPosition(position = 26)
    @JsonProperty("ATTRIBUTE5")
    private String attribute5;

    @CsvBindByPosition(position = 27)
    @JsonProperty("ATTRIBUTE6")
    private String attribute6;

    @CsvBindByPosition(position = 28)
    @JsonProperty("ATTRIBUTE7")
    private String attribute7;

    @CsvBindByPosition(position = 29)
    @JsonProperty("ATTRIBUTE8")
    private String attribute8;

    @CsvBindByPosition(position = 30)
    @JsonProperty("ATTRIBUTE9")
    private String attribute9;

    @CsvBindByPosition(position = 31)
    @JsonProperty("ATTRIBUTE10")
    private String attribute10;

    @CsvBindByPosition(position = 32)
    @JsonProperty("ATTRIBUTE11")
    private String attribute11;

    @CsvBindByPosition(position = 33)
    @JsonProperty("ATTRIBUTE12")
    private String attribute12;

    @CsvBindByPosition(position = 34)
    @JsonProperty("ATTRIBUTE13")
    private String attribute13;

    @CsvBindByPosition(position = 35)
    @JsonProperty("ATTRIBUTE14")
    private String attribute14;

    @CsvBindByPosition(position = 36)
    @JsonProperty("ATTRIBUTE15")
    private String attribute15;

    @CsvBindByPosition(position = 37)
    @JsonProperty("ATTRIBUTE16")
    private String attribute16;

    @CsvBindByPosition(position = 38)
    @JsonProperty("ATTRIBUTE17")
    private String attribute17;

    @CsvBindByPosition(position = 39)
    @JsonProperty("ATTRIBUTE18")
    private String attribute18;

    @CsvBindByPosition(position = 40)
    @JsonProperty("ATTRIBUTE19")
    private String attribute19;

    @CsvBindByPosition(position = 41)
    @JsonProperty("ATTRIBUTE20")
    private String attribute20;

    @CsvBindByPosition(position = 42)
    @JsonProperty("ATTRIBUTE_DATE1")
    private String attributeDate1;

    @CsvBindByPosition(position = 43)
    @JsonProperty("ATTRIBUTE_DATE2")
    private String attributeDate2;

    @CsvBindByPosition(position = 44)
    @JsonProperty("ATTRIBUTE_DATE3")
    private String attributeDate3;

    @CsvBindByPosition(position = 45)
    @JsonProperty("ATTRIBUTE_DATE4")
    private String attributeDate4;

    @CsvBindByPosition(position = 46)
    @JsonProperty("ATTRIBUTE_DATE5")
    private String attributeDate5;

    @CsvBindByPosition(position = 47)
    @JsonProperty("ATTRIBUTE_DATE6")
    private String attributeDate6;

    @CsvBindByPosition(position = 48)
    @JsonProperty("ATTRIBUTE_DATE7")
    private String attributeDate7;

    @CsvBindByPosition(position = 49)
    @JsonProperty("ATTRIBUTE_DATE8")
    private String attributeDate8;

    @CsvBindByPosition(position = 50)
    @JsonProperty("ATTRIBUTE_DATE9")
    private String attributeDate9;

    @CsvBindByPosition(position = 51)
    @JsonProperty("ATTRIBUTE_DATE10")
    private String attributeDate10;

    @CsvBindByPosition(position = 52)
    @JsonProperty("ATTRIBUTE_NUMBER1")
    private String attributeNumber1;

    @CsvBindByPosition(position = 53)
    @JsonProperty("ATTRIBUTE_NUMBER2")
    private String attributeNumber2;

    @CsvBindByPosition(position = 54)
    @JsonProperty("ATTRIBUTE_NUMBER3")
    private String attributeNumber3;

    @CsvBindByPosition(position = 55)
    @JsonProperty("ATTRIBUTE_NUMBER4")
    private String attributeNumber4;

    @CsvBindByPosition(position = 56)
    @JsonProperty("ATTRIBUTE_NUMBER5")
    private String attributeNumber5;

    @CsvBindByPosition(position = 57)
    @JsonProperty("ATTRIBUTE_NUMBER6")
    private String attributeNumber6;

    @CsvBindByPosition(position = 58)
    @JsonProperty("ATTRIBUTE_NUMBER7")
    private String attributeNumber7;

    @CsvBindByPosition(position = 59)
    @JsonProperty("ATTRIBUTE_NUMBER8")
    private String attributeNumber8;

    @CsvBindByPosition(position = 60)
    @JsonProperty("ATTRIBUTE_NUMBER9")
    private String attributeNumber9;

    @CsvBindByPosition(position = 61)
    @JsonProperty("ATTRIBUTE_NUMBER10")
    private String attributeNumber10;

    @CsvBindByPosition(position = 62)
    @JsonProperty("ATTRIBUTE_TIMESTAMP1")
    private String attributeTimestamp1;

    @CsvBindByPosition(position = 63)
    @JsonProperty("ATTRIBUTE_TIMESTAMP2")
    private String attributeTimestamp2;

    @CsvBindByPosition(position = 64)
    @JsonProperty("ATTRIBUTE_TIMESTAMP3")
    private String attributeTimestamp3;

    @CsvBindByPosition(position = 65)
    @JsonProperty("ATTRIBUTE_TIMESTAMP4")
    private String attributeTimestamp4;

    @CsvBindByPosition(position = 66)
    @JsonProperty("ATTRIBUTE_TIMESTAMP5")
    private String attributeTimestamp5;

    @CsvBindByPosition(position = 67)
    @JsonProperty("ATTRIBUTE_TIMESTAMP6")
    private String attributeTimestamp6;

    @CsvBindByPosition(position = 68)
    @JsonProperty("ATTRIBUTE_TIMESTAMP7")
    private String attributeTimestamp7;

    @CsvBindByPosition(position = 69)
    @JsonProperty("ATTRIBUTE_TIMESTAMP8")
    private String attributeTimestamp8;

    @CsvBindByPosition(position = 70)
    @JsonProperty("ATTRIBUTE_TIMESTAMP9")
    private String attributeTimestamp9;

    @CsvBindByPosition(position = 71)
    @JsonProperty("ATTRIBUTE_TIMESTAMP10")
    private String attributeTimestamp10;

    @CsvBindByPosition(position = 72)
    @JsonProperty("Unit Weight")
    private String unitWeight;

    @CsvBindByPosition(position = 73)
    @JsonProperty("Weight UOM")
    private String weightUOM;

    @CsvBindByPosition(position = 74)
    @JsonProperty("Weight UOM Name")
    private String weightUOMName;

    @CsvBindByPosition(position = 75)
    @JsonProperty("Unit Volumn")
    private String unitVolumn;

    @CsvBindByPosition(position = 76)
    @JsonProperty("Volume UOM")
    private String volumeUOM;

    @CsvBindByPosition(position = 77)
    @JsonProperty("Volume UOM Name")
    private String volumeUOMName;

    @CsvBindByPosition(position = 78)
    @JsonProperty("Template Name")
    private String templateName;

    @CsvBindByPosition(position = 79)
    @JsonProperty("ITEM_ATTRIBUTE_CATEGORY")
    private String itemAttributeCategory;

    @CsvBindByPosition(position = 80)
    @JsonProperty("ITEM_ATTRIBUTE1")
    private String itemAttribute1;

    @CsvBindByPosition(position = 81)
    @JsonProperty("ITEM_ATTRIBUTE2")
    private String itemAttribute2;

    @CsvBindByPosition(position = 82)
    @JsonProperty("ITEM_ATTRIBUTE3")
    private String itemAttribute3;

    @CsvBindByPosition(position = 83)
    @JsonProperty("ITEM_ATTRIBUTE4")
    private String itemAttribute4;

    @CsvBindByPosition(position = 84)
    @JsonProperty("ITEM_ATTRIBUTE5")
    private String itemAttribute5;

    @CsvBindByPosition(position = 85)
    @JsonProperty("ITEM_ATTRIBUTE6")
    private String itemAttribute6;

    @CsvBindByPosition(position = 86)
    @JsonProperty("ITEM_ATTRIBUTE7")
    private String itemAttribute7;

    @CsvBindByPosition(position = 87)
    @JsonProperty("ITEM_ATTRIBUTE8")
    private String itemAttribute8;

    @CsvBindByPosition(position = 88)
    @JsonProperty("ITEM_ATTRIBUTE9")
    private String itemAttribute9;

    @CsvBindByPosition(position = 89)
    @JsonProperty("ITEM_ATTRIBUTE10")
    private String itemAttribute10;

    @CsvBindByPosition(position = 90)
    @JsonProperty("ITEM_ATTRIBUTE11")
    private String itemAttribute11;

    @CsvBindByPosition(position = 91)
    @JsonProperty("ITEM_ATTRIBUTE12")
    private String itemAttribute12;

    @CsvBindByPosition(position = 92)
    @JsonProperty("ITEM_ATTRIBUTE13")
    private String itemAttribute13;

    @CsvBindByPosition(position = 93)
    @JsonProperty("ITEM_ATTRIBUTE14")
    private String itemAttribute14;

    @CsvBindByPosition(position = 94)
    @JsonProperty("ITEM_ATTRIBUTE15")
    private String itemAttribute15;

    @CsvBindByPosition(position = 95)
    @JsonProperty("Source Agreement Procurement BU")
    private String sourceAgreementProcurementBU;

    @CsvBindByPosition(position = 96)
    @JsonProperty("Source Agreement")
    private String sourceAgreement;

    @CsvBindByPosition(position = 97)
    @JsonProperty("Source Agreement Line")
    private String sourceAgreementLine;

    @CsvBindByPosition(position = 98)
    @JsonProperty("Discount Type Code")
    private String discountTypeCode;

    @CsvBindByPosition(position = 99)
    @JsonProperty("Discount")
    private String discount;

    @CsvBindByPosition(position = 100)
    @JsonProperty("Discount Reason")
    private String discountReason;

    @CsvBindByPosition(position = 101)
    @JsonProperty("Maximum Retainage Amount")
    private String maximumRetainageAmount;
}
