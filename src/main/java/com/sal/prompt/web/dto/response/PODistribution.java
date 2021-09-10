package com.sal.prompt.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class PODistribution {


    @CsvBindByPosition(position = 0)
    @JsonProperty("Interface Distribution Key")
    private String interfaceDistributionKey;

    @CsvBindByPosition(position = 1)
    @JsonProperty("Interface Line Location Key")
    private String interfaceLineLocationKey;

    @CsvBindByPosition(position = 2)
    @JsonProperty("Distribution")
    private String distribution;

    @CsvBindByPosition(position = 3)
    @JsonProperty("Deliver-to Location")
    private String deliverToLocation;

    @CsvBindByPosition(position = 4)
    @JsonProperty("Requester")
    private String requester;

    @CsvBindByPosition(position = 5)
    @JsonProperty("Subinventory")
    private String subinventory;

    @CsvBindByPosition(position = 6)
    @JsonProperty("Amount")
    private String amount;

    @CsvBindByPosition(position = 7)
    @JsonProperty("Quantity")
    private String quantity;

    @CsvBindByPosition(position = 8)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT1")
    private String chargeAccountSegment1;

    @CsvBindByPosition(position = 9)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT2")
    private String chargeAccountSegment2;

    @CsvBindByPosition(position = 10)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT3")
    private String chargeAccountSegment3;

    @CsvBindByPosition(position = 11)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT4")
    private String chargeAccountSegment4;

    @CsvBindByPosition(position = 12)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT5")
    private String chargeAccountSegment5;

    @CsvBindByPosition(position = 13)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT6")
    private String chargeAccountSegment6;

    @CsvBindByPosition(position = 14)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT7")
    private String chargeAccountSegment7;

    @CsvBindByPosition(position = 15)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT8")
    private String chargeAccountSegment8;

    @CsvBindByPosition(position = 16)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT9")
    private String chargeAccountSegment9;

    @CsvBindByPosition(position = 17)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT10")
    private String chargeAccountSegment10;

    @CsvBindByPosition(position = 18)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT11")
    private String chargeAccountSegment11;

    @CsvBindByPosition(position = 19)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT12")
    private String chargeAccountSegment12;

    @CsvBindByPosition(position = 20)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT13")
    private String chargeAccountSegment13;

    @CsvBindByPosition(position = 21)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT14")
    private String chargeAccountSegment14;

    @CsvBindByPosition(position = 22)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT15")
    private String chargeAccountSegment15;

    @CsvBindByPosition(position = 23)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT16")
    private String chargeAccountSegment16;

    @CsvBindByPosition(position = 24)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT17")
    private String chargeAccountSegment17;

    @CsvBindByPosition(position = 25)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT18")
    private String chargeAccountSegment18;

    @CsvBindByPosition(position = 26)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT19")
    private String chargeAccountSegment19;

    @CsvBindByPosition(position = 27)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT20")
    private String chargeAccountSegment20;

    @CsvBindByPosition(position = 28)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT21")
    private String chargeAccountSegment21;

    @CsvBindByPosition(position = 29)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT22")
    private String chargeAccountSegment22;

    @CsvBindByPosition(position = 30)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT23")
    private String chargeAccountSegment23;

    @CsvBindByPosition(position = 31)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT24")
    private String chargeAccountSegment24;

    @CsvBindByPosition(position = 32)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT25")
    private String chargeAccountSegment25;

    @CsvBindByPosition(position = 33)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT26")
    private String chargeAccountSegment26;

    @CsvBindByPosition(position = 34)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT27")
    private String chargeAccountSegment27;

    @CsvBindByPosition(position = 35)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT28")
    private String chargeAccountSegment28;

    @CsvBindByPosition(position = 36)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT29")
    private String chargeAccountSegment29;

    @CsvBindByPosition(position = 37)
    @JsonProperty("CHARGE_ACCOUNT_SEGMENT30")
    private String chargeAccountSegment30;

    @CsvBindByPosition(position = 38)
    @JsonProperty("DESTINATION_CONTEXT")
    private String destinationContext;

    @CsvBindByPosition(position = 39)
    @JsonProperty("Project Number")
    private String projectNumber;

    @CsvBindByPosition(position = 40)
    @JsonProperty("Task Number")
    private String taskNumber;

    @CsvBindByPosition(position = 41)
    @JsonProperty("Expenditure Item Date")
    private String expenditureItemDate;

    @CsvBindByPosition(position = 42)
    @JsonProperty("Expenditure Type")
    private String expenditureType;

    @CsvBindByPosition(position = 43)
    @JsonProperty("Expenditure Organization")
    private String expenditureOrganization;

    @CsvBindByPosition(position = 44)
    @JsonProperty("Billable")
    private String billable;

    @CsvBindByPosition(position = 45)
    @JsonProperty("Capitalizable")
    private String capitalizable;

    @CsvBindByPosition(position = 46)
    @JsonProperty("Work Type")
    private String workType;

    @CsvBindByPosition(position = 47)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE1")
    private String pjcReservedAttribute1;

    @CsvBindByPosition(position = 48)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE2")
    private String pjcReservedAttribute2;

    @CsvBindByPosition(position = 49)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE3")
    private String pjcReservedAttribute3;

    @CsvBindByPosition(position = 50)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE4")
    private String pjcReservedAttribute4;

    @CsvBindByPosition(position = 51)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE5")
    private String pjcReservedAttribute5;

    @CsvBindByPosition(position = 52)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE6")
    private String pjcReservedAttribute6;

    @CsvBindByPosition(position = 53)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE7")
    private String pjcReservedAttribute7;

    @CsvBindByPosition(position = 54)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE8")
    private String pjcReservedAttribute8;

    @CsvBindByPosition(position = 55)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE9")
    private String pjcReservedAttribute9;

    @CsvBindByPosition(position = 56)
    @JsonProperty("PJC_RESERVED_ATTRIBUTE10")
    private String pjcReservedAttribute10;

    @CsvBindByPosition(position = 57)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE1")
    private String pjcUserDefAttribute1;

    @CsvBindByPosition(position = 58)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE2")
    private String pjcUserDefAttribute2;

    @CsvBindByPosition(position = 59)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE3")
    private String pjcUserDefAttribute3;

    @CsvBindByPosition(position = 60)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE4")
    private String pjcUserDefAttribute4;

    @CsvBindByPosition(position = 61)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE5")
    private String pjcUserDefAttribute5;

    @CsvBindByPosition(position = 62)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE6")
    private String pjcUserDefAttribute6;

    @CsvBindByPosition(position = 63)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE7")
    private String pjcUserDefAttribute7;

    @CsvBindByPosition(position = 64)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE8")
    private String pjcUserDefAttribute8;

    @CsvBindByPosition(position = 65)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE9")
    private String pjcUserDefAttribute9;

    @CsvBindByPosition(position = 66)
    @JsonProperty("PJC_USER_DEF_ATTRIBUTE10")
    private String pjcUserDefAttribute10;

    @CsvBindByPosition(position = 67)
    @JsonProperty("Rate")
    private String rate;

    @CsvBindByPosition(position = 68)
    @JsonProperty("Rate Date")
    private String rateDate;

    @CsvBindByPosition(position = 69)
    @JsonProperty("ATTRIBUTE_CATEGORY")
    private String attributeCategory;

    @CsvBindByPosition(position = 70)
    @JsonProperty("ATTRIBUTE1")
    private String attribute1;

    @CsvBindByPosition(position = 71)
    @JsonProperty("ATTRIBUTE2")
    private String attribute2;

    @CsvBindByPosition(position = 72)
    @JsonProperty("ATTRIBUTE3")
    private String attribute3;

    @CsvBindByPosition(position = 73)
    @JsonProperty("ATTRIBUTE4")
    private String attribute4;

    @CsvBindByPosition(position = 74)
    @JsonProperty("ATTRIBUTE5")
    private String attribute5;

    @CsvBindByPosition(position = 75)
    @JsonProperty("ATTRIBUTE6")
    private String attribute6;

    @CsvBindByPosition(position = 76)
    @JsonProperty("ATTRIBUTE7")
    private String attribute7;

    @CsvBindByPosition(position = 77)
    @JsonProperty("ATTRIBUTE8")
    private String attribute8;

    @CsvBindByPosition(position = 78)
    @JsonProperty("ATTRIBUTE9")
    private String attribute9;

    @CsvBindByPosition(position = 79)
    @JsonProperty("ATTRIBUTE10")
    private String attribute10;

    @CsvBindByPosition(position = 80)
    @JsonProperty("ATTRIBUTE11")
    private String attribute11;

    @CsvBindByPosition(position = 81)
    @JsonProperty("ATTRIBUTE12")
    private String attribute12;

    @CsvBindByPosition(position = 82)
    @JsonProperty("ATTRIBUTE13")
    private String attribute13;

    @CsvBindByPosition(position = 83)
    @JsonProperty("ATTRIBUTE14")
    private String attribute14;

    @CsvBindByPosition(position = 84)
    @JsonProperty("ATTRIBUTE15")
    private String attribute15;

    @CsvBindByPosition(position = 85)
    @JsonProperty("ATTRIBUTE16")
    private String attribute16;

    @CsvBindByPosition(position = 86)
    @JsonProperty("ATTRIBUTE17")
    private String attribute17;

    @CsvBindByPosition(position = 87)
    @JsonProperty("ATTRIBUTE18")
    private String attribute18;

    @CsvBindByPosition(position = 88)
    @JsonProperty("ATTRIBUTE19")
    private String attribute19;

    @CsvBindByPosition(position = 89)
    @JsonProperty("ATTRIBUTE20")
    private String attribute20;

    @CsvBindByPosition(position = 90)
    @JsonProperty("ATTRIBUTE_DATE1")
    private String attributeDate1;

    @CsvBindByPosition(position = 91)
    @JsonProperty("ATTRIBUTE_DATE2")
    private String attributeDate2;

    @CsvBindByPosition(position = 92)
    @JsonProperty("ATTRIBUTE_DATE3")
    private String attributeDate3;

    @CsvBindByPosition(position = 93)
    @JsonProperty("ATTRIBUTE_DATE4")
    private String attributeDate4;

    @CsvBindByPosition(position = 94)
    @JsonProperty("ATTRIBUTE_DATE5")
    private String attributeDate5;

    @CsvBindByPosition(position = 95)
    @JsonProperty("ATTRIBUTE_DATE6")
    private String attributeDate6;

    @CsvBindByPosition(position = 96)
    @JsonProperty("ATTRIBUTE_DATE7")
    private String attributeDate7;

    @CsvBindByPosition(position = 97)
    @JsonProperty("ATTRIBUTE_DATE8")
    private String attributeDate8;

    @CsvBindByPosition(position = 98)
    @JsonProperty("ATTRIBUTE_DATE9")
    private String attributeDate9;

    @CsvBindByPosition(position = 99)
    @JsonProperty("ATTRIBUTE_DATE10")
    private String attributeDate10;

    @CsvBindByPosition(position = 100)
    @JsonProperty("ATTRIBUTE_NUMBER1")
    private String attributeNumber1;

    @CsvBindByPosition(position = 101)
    @JsonProperty("ATTRIBUTE_NUMBER2")
    private String attributeNumber2;

    @CsvBindByPosition(position = 102)
    @JsonProperty("ATTRIBUTE_NUMBER3")
    private String attributeNumber3;

    @CsvBindByPosition(position = 103)
    @JsonProperty("ATTRIBUTE_NUMBER4")
    private String attributeNumber4;

    @CsvBindByPosition(position = 104)
    @JsonProperty("ATTRIBUTE_NUMBER5")
    private String attributeNumber5;

    @CsvBindByPosition(position = 105)
    @JsonProperty("ATTRIBUTE_NUMBER6")
    private String attributeNumber6;

    @CsvBindByPosition(position = 106)
    @JsonProperty("ATTRIBUTE_NUMBER7")
    private String attributeNumber7;

    @CsvBindByPosition(position = 107)
    @JsonProperty("ATTRIBUTE_NUMBER8")
    private String attributeNumber8;

    @CsvBindByPosition(position = 108)
    @JsonProperty("ATTRIBUTE_NUMBER9")
    private String attributeNumber9;

    @CsvBindByPosition(position = 109)
    @JsonProperty("ATTRIBUTE_NUMBER10")
    private String attributeNumber10;

    @CsvBindByPosition(position = 110)
    @JsonProperty("ATTRIBUTE_TIMESTAMP1")
    private String attributeTimestamp1;

    @CsvBindByPosition(position = 111)
    @JsonProperty("ATTRIBUTE_TIMESTAMP2")
    private String attributeTimestamp2;

    @CsvBindByPosition(position = 112)
    @JsonProperty("ATTRIBUTE_TIMESTAMP3")
    private String attributeTimestamp3;

    @CsvBindByPosition(position = 113)
    @JsonProperty("ATTRIBUTE_TIMESTAMP4")
    private String attributeTimestamp4;

    @CsvBindByPosition(position = 114)
    @JsonProperty("ATTRIBUTE_TIMESTAMP5")
    private String attributeTimestamp5;

    @CsvBindByPosition(position = 115)
    @JsonProperty("ATTRIBUTE_TIMESTAMP6")
    private String attributeTimestamp6;

    @CsvBindByPosition(position = 116)
    @JsonProperty("ATTRIBUTE_TIMESTAMP7")
    private String attributeTimestamp7;

    @CsvBindByPosition(position = 117)
    @JsonProperty("ATTRIBUTE_TIMESTAMP8")
    private String attributeTimestamp8;

    @CsvBindByPosition(position = 118)
    @JsonProperty("ATTRIBUTE_TIMESTAMP9")
    private String attributeTimestamp9;

    @CsvBindByPosition(position = 119)
    @JsonProperty("ATTRIBUTE_TIMESTAMP10")
    private String attributeTimestamp10;

    @CsvBindByPosition(position = 120)
    @JsonProperty("Requester E-mail")
    private String requesterEMail;

    @CsvBindByPosition(position = 121)
    @JsonProperty("Budget Date")
    private String budgetDate;

    @CsvBindByPosition(position = 122)
    @JsonProperty("Contract Number")
    private String contractNumber;

    @CsvBindByPosition(position = 123)
    @JsonProperty("Funding Source")
    private String fundingSource;
}

