package com.sal.prompt.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class ReceiptLineResponse {

    @CsvBindByPosition(position =0)
    @JsonProperty("Interface Line Number")
    @CsvBindByPosition(position =1 )
    private String interfaceLineNumber;

    @CsvBindByPosition(position =2 )
    @JsonProperty("Transaction Type")
    @CsvBindByPosition(position =3 )
    private String transactionType;

    @CsvBindByPosition(position =4 )
    @JsonProperty("Auto Transact Code")
    @CsvBindByPosition(position =5 )
    private String autoTransactCode;

    @CsvBindByPosition(position =6 )
    @JsonProperty("Transaction Date")
    @CsvBindByPosition(position =7 )
    private String transactionDate;

    @CsvBindByPosition(position =8 )
    @JsonProperty("Source Document Code")
    @CsvBindByPosition(position =9 )
    private String sourceDocumentCode;

    @CsvBindByPosition(position =10 )
    @JsonProperty("Receipt Source Code")
    @CsvBindByPosition(position =11 )
    private String receiptSourceCode;

    @CsvBindByPosition(position =12 )
    @JsonProperty("Header Interface Number")
    @CsvBindByPosition(position =13 )
    private String headerInterfaceNumber;

    @CsvBindByPosition(position =14 )
    @JsonProperty("Parent Transaction Id")
    @CsvBindByPosition(position =15 )
    private String parentTransactionId;

    @CsvBindByPosition(position =16 )
    @JsonProperty("Parent Interface Line Number")
    @CsvBindByPosition(position =17 )
    private String parentInterfaceLineNumber;

    @CsvBindByPosition(position =18 )
    @JsonProperty("Organization Code")
    @CsvBindByPosition(position =19 )
    private String organizationCode;

    @CsvBindByPosition(position =20 )
    @JsonProperty("Item Number")
    @CsvBindByPosition(position =21 )
    private String itemNumber;

    @CsvBindByPosition(position =22 )
    @JsonProperty("Item Description")
    @CsvBindByPosition(position =23 )
    private String itemDescription;

    @CsvBindByPosition(position =24 )
    @JsonProperty("Item Revision")
    @CsvBindByPosition(position =25 )
    private String itemRevision;

    @CsvBindByPosition(position =26 )
    @JsonProperty("Document Number")
    @CsvBindByPosition(position =27 )
    private String documentNumber;

    @CsvBindByPosition(position =28 )
    @JsonProperty("Document Line Number")
    @CsvBindByPosition(position =29 )
    private String documentLineNumber;

    @CsvBindByPosition(position =30 )
    @JsonProperty("Document Schedule Number")
    @CsvBindByPosition(position =31 )
    private String documentScheduleNumber;

    @CsvBindByPosition(position =32 )
    @JsonProperty("Document Distribution Number")
    @CsvBindByPosition(position =33 )
    private String documentDistributionNumber;

    @CsvBindByPosition(position =34 )
    @JsonProperty("Business Unit")
    @CsvBindByPosition(position =35 )
    private String businessUnit;

    @CsvBindByPosition(position =36 )
    @JsonProperty("Shipment Number")
    @CsvBindByPosition(position =37 )
    private String shipmentNumber;

    @CsvBindByPosition(position =38 )
    @JsonProperty("Expected Receipt Date")
    @CsvBindByPosition(position =39 )
    private String expectedReceiptDate;

    @CsvBindByPosition(position =40 )
    @JsonProperty("Subinventory")
    @CsvBindByPosition(position =41 )
    private String subinventory;

    @CsvBindByPosition(position =42 )
    @JsonProperty("Locator")
    @CsvBindByPosition(position =43 )
    private String locator;

    @CsvBindByPosition(position =44 )
    @JsonProperty("Quantity")
    @CsvBindByPosition(position =45 )
    private String quantity;

    @CsvBindByPosition(position =46 )
    @JsonProperty("UOM")
    @CsvBindByPosition(position =47 )
    private String uom;

    @CsvBindByPosition(position =48 )
    @JsonProperty("Primary Quantity")
    @CsvBindByPosition(position =49 )
    private String primaryQuantity;

    @CsvBindByPosition(position =50 )
    @JsonProperty("Primary UOM")
    @CsvBindByPosition(position =51 )
    private String primaryUOM;

    @CsvBindByPosition(position =52 )
    @JsonProperty("Secondary Quantity")
    @CsvBindByPosition(position =53 )
    private String secondaryQuantity;

    @CsvBindByPosition(position =54 )
    @JsonProperty("Secondary UOM")
    @CsvBindByPosition(position =55 )
    private String secondaryUOM;

    @CsvBindByPosition(position =56 )
    @JsonProperty("Supplier Name")
    @CsvBindByPosition(position =57 )
    private String supplierName;

    @CsvBindByPosition(position =58 )
    @JsonProperty("Supplier Number")
    @CsvBindByPosition(position =59 )
    private String supplierNumber;

    @CsvBindByPosition(position =60 )
    @JsonProperty("Supplier Site Code")
    @CsvBindByPosition(position =61 )
    private String supplierSiteCode;

    @CsvBindByPosition(position =62 )
    @JsonProperty("Customer Name")
    @CsvBindByPosition(position =63 )
    private String customerName;

    @CsvBindByPosition(position =64 )
    @JsonProperty("Customer Number")
    @CsvBindByPosition(position =65 )
    private String customerNumber;

    @CsvBindByPosition(position =66 )
    @JsonProperty("Customer Account Number")
    @CsvBindByPosition(position =67 )
    private String customerAccountNumber;

    @CsvBindByPosition(position =68 )
    @JsonProperty("Ship To Location Code")
    @CsvBindByPosition(position =69 )
    private String shipToLocationCode;

    @CsvBindByPosition(position =70 )
    @JsonProperty("Location Code")
    @CsvBindByPosition(position =71 )
    private String locationCode;

    @CsvBindByPosition(position =72 )
    @JsonProperty("Reason Name")
    @CsvBindByPosition(position =73 )
    private String reasonName;

    @CsvBindByPosition(position =74 )
    @JsonProperty("Deliver to Person Name")
    @CsvBindByPosition(position =75 )
    private String deliverToPersonName;

    @CsvBindByPosition(position =76 )
    @JsonProperty("Deliver to Location Code")
    @CsvBindByPosition(position =77 )
    private String deliverToLocationCode;

    @CsvBindByPosition(position =78 )
    @JsonProperty("Receipt Exception Flag")
    @CsvBindByPosition(position =79 )
    private String receiptExceptionFlag;

    @CsvBindByPosition(position =80 )
    @JsonProperty("Routing Header ID")
    @CsvBindByPosition(position =81 )
    private String routingHeaderID;

    @CsvBindByPosition(position =82 )
    @JsonProperty("Destination Type Code")
    @CsvBindByPosition(position =83 )
    private String destinationTypeCode;

    @CsvBindByPosition(position =84 )
    @JsonProperty("Interface Source Code")
    @CsvBindByPosition(position =85 )
    private String interfaceSourceCode;

    @CsvBindByPosition(position =86 )
    @JsonProperty("Interface Source Line ID")
    @CsvBindByPosition(position =87 )
    private String interfaceSourceLineID;

    @CsvBindByPosition(position =88 )
    @JsonProperty("Amount")
    @CsvBindByPosition(position =89 )
    private String amount;

    @CsvBindByPosition(position =90 )
    @JsonProperty("Currency Code")
    @CsvBindByPosition(position =91 )
    private String currencyCode;

    @CsvBindByPosition(position =92 )
    @JsonProperty("Currency Conversion Type")
    @CsvBindByPosition(position =93 )
    private String currencyConversionType;

    @CsvBindByPosition(position =94 )
    @JsonProperty("Currency Conversion Rate")
    @CsvBindByPosition(position =95 )
    private String currencyConversionRate;

    @CsvBindByPosition(position =96 )
    @JsonProperty("Currency Conversion Date")
    @CsvBindByPosition(position =97 )
    private String currencyConversionDate;

    @CsvBindByPosition(position =98 )
    @JsonProperty("Inspection Status Code")
    @CsvBindByPosition(position =99 )
    private String inspectionStatusCode;

    @CsvBindByPosition(position =100 )
    @JsonProperty("Inspection Quality Code")
    @CsvBindByPosition(position =101 )
    private String inspectionQualityCode;

    @CsvBindByPosition(position =102 )
    @JsonProperty("From Organization Code")
    @CsvBindByPosition(position =103 )
    private String fromOrganizationCode;

    @CsvBindByPosition(position =104 )
    @JsonProperty("From Subinventory")
    @CsvBindByPosition(position =105 )
    private String fromSubinventory;

    @CsvBindByPosition(position =106 )
    @JsonProperty("From Locator")
    @CsvBindByPosition(position =107 )
    private String fromLocator;

    @CsvBindByPosition(position =108 )
    @JsonProperty("Carrier Name")
    @CsvBindByPosition(position =109 )
    private String carrierName;

    @CsvBindByPosition(position =110 )
    @JsonProperty("Bill of Lading")
    @CsvBindByPosition(position =111 )
    private String billOfLading;

    @CsvBindByPosition(position =112 )
    @JsonProperty("Packing Slip")
    @CsvBindByPosition(position =113 )
    private String packingSlip;

    @CsvBindByPosition(position =114 )
    @JsonProperty("Shipped Date")
    @CsvBindByPosition(position =115 )
    private String shippedDate;

    @CsvBindByPosition(position =116 )
    @JsonProperty("Number of Containers")
    @CsvBindByPosition(position =117 )
    private String numberOfContainers;

    @CsvBindByPosition(position =118 )
    @JsonProperty("Waybill")
    @CsvBindByPosition(position =119 )
    private String waybill;

    @CsvBindByPosition(position =120 )
    @JsonProperty("RMA Reference")
    @CsvBindByPosition(position =121 )
    private String rMAReference;

    @CsvBindByPosition(position =122 )
    @JsonProperty("Comments")
    @CsvBindByPosition(position =123 )
    private String comments;

    @CsvBindByPosition(position =124 )
    @JsonProperty("Truck Number")
    @CsvBindByPosition(position =125 )
    private String truckNumber;

    @CsvBindByPosition(position =126 )
    @JsonProperty("Container Number")
    @CsvBindByPosition(position =127 )
    private String containerNumber;

    @CsvBindByPosition(position =128 )
    @JsonProperty("Substitute Item Number")
    @CsvBindByPosition(position =129 )
    private String substituteItemNumber;

    @CsvBindByPosition(position =130 )
    @JsonProperty("Notice Unit Price")
    @CsvBindByPosition(position =131 )
    private String noticeUnitPrice;

    @CsvBindByPosition(position =132 )
    @JsonProperty("Item Category")
    @CsvBindByPosition(position =133 )
    private String itemCategory;

    @CsvBindByPosition(position =134 )
    @JsonProperty("Intransit Owning Organization Code")
    @CsvBindByPosition(position =135 )
    private String intransitOwningOrganizationCode;

    @CsvBindByPosition(position =136 )
    @JsonProperty("Routing Name")
    @CsvBindByPosition(position =137 )
    private String routingName;

    @CsvBindByPosition(position =138 )
    @JsonProperty("Barcode Label")
    @CsvBindByPosition(position =139 )
    private String barcodeLabel;

    @CsvBindByPosition(position =140 )
    @JsonProperty("Country of Origin Code")
    @CsvBindByPosition(position =141 )
    private String countryOfOriginCode;

    @CsvBindByPosition(position =142 )
    @JsonProperty("Create Debit Memo Flag")
    @CsvBindByPosition(position =143 )
    private String createDebitMemoFlag;

    @CsvBindByPosition(position =144 )
    @JsonProperty("Source Packing Unit")
    @CsvBindByPosition(position =145 )
    private String sourcePackingUnit;

    @CsvBindByPosition(position =146 )
    @JsonProperty("Transfer Packing Unit")
    @CsvBindByPosition(position =147 )
    private String transferPackingUnit;

    @CsvBindByPosition(position =148 )
    @JsonProperty("Packing Unit Group Number")
    @CsvBindByPosition(position =149 )
    private String packingUnitGroupNumber;

    @CsvBindByPosition(position =150 )
    @JsonProperty("ASN Line Number")
    @CsvBindByPosition(position =151 )
    private String aSNLineNumber;

    @CsvBindByPosition(position =152 )
    @JsonProperty("Employee Name")
    @CsvBindByPosition(position =153 )
    private String employeeName;

    @CsvBindByPosition(position =154 )
    @JsonProperty("Source Transaction Number")
    @CsvBindByPosition(position =155 )
    private String sourceTransactionNumber;

    @CsvBindByPosition(position =156 )
    @JsonProperty("Parent Source Transaction Number")
    @CsvBindByPosition(position =157 )
    private String parentSourceTransactionNumber;

    @CsvBindByPosition(position =158 )
    @JsonProperty("Parent Interface Transaction Id")
    @CsvBindByPosition(position =159 )
    private String parentInterfaceTransactionId;

    @CsvBindByPosition(position =160 )
    @JsonProperty("Matching Basis")
    @CsvBindByPosition(position =161 )
    private String matchingBasis;

    @CsvBindByPosition(position =162 )
    @JsonProperty("Receipt Advice Logistics Outsourcer Customer Party Name")
    @CsvBindByPosition(position =163 )
    private String receiptAdviceLogisticsOutsourcerCustomerPartyName;

    @CsvBindByPosition(position =164 )
    @JsonProperty("Receipt Advice Document Number")
    @CsvBindByPosition(position =165 )
    private String receiptAdviceDocumentNumber;

    @CsvBindByPosition(position =166 )
    @JsonProperty("Receipt Advice Document Line Number")
    @CsvBindByPosition(position =167 )
    private String receiptAdviceDocumentLineNumber;

    @CsvBindByPosition(position =168 )
    @JsonProperty("Receipt Advice Notes to Receiver")
    @CsvBindByPosition(position =169 )
    private String receiptAdviceNotesToReceiver;

    @CsvBindByPosition(position =170 )
    @JsonProperty("Receipt Advice Vendor Site Name")
    @CsvBindByPosition(position =171 )
    private String receiptAdviceVendorSiteName;

    @CsvBindByPosition(position =172 )
    @JsonProperty("Attribute Category")
    @CsvBindByPosition(position =173 )
    private String attributeCategory;

    @CsvBindByPosition(position =174 )
    @JsonProperty("ATTRIBUTE1")
    @CsvBindByPosition(position =175 )
    private String attribute1;

    @CsvBindByPosition(position =176 )
    @JsonProperty("ATTRIBUTE2")
    @CsvBindByPosition(position =177 )
    private String attribute2;

    @CsvBindByPosition(position =178 )
    @JsonProperty("ATTRIBUTE3")
    @CsvBindByPosition(position =179 )
    private String attribute3;

    @CsvBindByPosition(position =180 )
    @JsonProperty("ATTRIBUTE4")
    @CsvBindByPosition(position =181 )
    private String attribute4;

    @CsvBindByPosition(position =182 )
    @JsonProperty("ATTRIBUTE5")
    @CsvBindByPosition(position =183 )
    private String attribute5;

    @CsvBindByPosition(position =184 )
    @JsonProperty("ATTRIBUTE6")
    @CsvBindByPosition(position =185 )
    private String attribute6;

    @CsvBindByPosition(position =186 )
    @JsonProperty("ATTRIBUTE7")
    @CsvBindByPosition(position =187 )
    private String attribute7;

    @CsvBindByPosition(position =188 )
    @JsonProperty("ATTRIBUTE8")
    @CsvBindByPosition(position =189 )
    private String attribute8;

    @CsvBindByPosition(position =190 )
    @JsonProperty("ATTRIBUTE9")
    @CsvBindByPosition(position =191 )
    private String attribute9;

    @CsvBindByPosition(position =192 )
    @JsonProperty("ATTRIBUTE10")
    @CsvBindByPosition(position =193 )
    private String attribute10;

    @CsvBindByPosition(position =194 )
    @JsonProperty("ATTRIBUTE11")
    @CsvBindByPosition(position =195 )
    private String attribute11;

    @CsvBindByPosition(position =196 )
    @JsonProperty("ATTRIBUTE12")
    @CsvBindByPosition(position =197 )
    private String attribute12;

    @CsvBindByPosition(position =198 )
    @JsonProperty("ATTRIBUTE13")
    @CsvBindByPosition(position =199 )
    private String attribute13;

    @CsvBindByPosition(position =200 )
    @JsonProperty("ATTRIBUTE14")
    @CsvBindByPosition(position =201 )
    private String attribute14;

    @CsvBindByPosition(position =202 )
    @JsonProperty("ATTRIBUTE15")
    @CsvBindByPosition(position =203 )
    private String attribute15;

    @CsvBindByPosition(position =204 )
    @JsonProperty("ATTRIBUTE16")
    @CsvBindByPosition(position =205 )
    private String attribute16;

    @CsvBindByPosition(position =206 )
    @JsonProperty("ATTRIBUTE17")
    @CsvBindByPosition(position =207 )
    private String attribute17;

    @CsvBindByPosition(position =208 )
    @JsonProperty("ATTRIBUTE18")
    @CsvBindByPosition(position =209 )
    private String attribute18;

    @CsvBindByPosition(position =210 )
    @JsonProperty("ATTRIBUTE19")
    @CsvBindByPosition(position =211 )
    private String attribute19;

    @CsvBindByPosition(position =212 )
    @JsonProperty("ATTRIBUTE20")
    @CsvBindByPosition(position =213 )
    private String attribute20;

    @CsvBindByPosition(position =214 )
    @JsonProperty("ATTRIBUTE_NUMBER1")
    @CsvBindByPosition(position =215 )
    private String attributeNumber1;

    @CsvBindByPosition(position =216 )
    @JsonProperty("ATTRIBUTE_NUMBER2")
    @CsvBindByPosition(position =217 )
    private String attributeNumber2;

    @CsvBindByPosition(position =218 )
    @JsonProperty("ATTRIBUTE_NUMBER3")
    @CsvBindByPosition(position =219 )
    private String attributeNumber3;

    @CsvBindByPosition(position =220 )
    @JsonProperty("ATTRIBUTE_NUMBER4")
    @CsvBindByPosition(position =221 )
    private String attributeNumber4;

    @CsvBindByPosition(position =222 )
    @JsonProperty("ATTRIBUTE_NUMBER5")
    @CsvBindByPosition(position =223 )
    private String attributeNumber5;

    @CsvBindByPosition(position =224 )
    @JsonProperty("ATTRIBUTE_NUMBER6")
    @CsvBindByPosition(position =225 )
    private String attributeNumber6;

    @CsvBindByPosition(position =226 )
    @JsonProperty("ATTRIBUTE_NUMBER7")
    @CsvBindByPosition(position =227 )
    private String attributeNumber7;

    @CsvBindByPosition(position =228 )
    @JsonProperty("ATTRIBUTE_NUMBER8")
    @CsvBindByPosition(position =229 )
    private String attributeNumber8;

    @CsvBindByPosition(position =230 )
    @JsonProperty("ATTRIBUTE_NUMBER9")
    @CsvBindByPosition(position =231 )
    private String attributeNumber9;

    @CsvBindByPosition(position =232 )
    @JsonProperty("ATTRIBUTE_NUMBER10")
    @CsvBindByPosition(position =233 )
    private String attributeNumber10;

    @CsvBindByPosition(position =234 )
    @JsonProperty("ATTRIBUTE_DATE1")
    @CsvBindByPosition(position =235 )
    private String attributeDate1;

    @CsvBindByPosition(position =236 )
    @JsonProperty("ATTRIBUTE_DATE2")
    @CsvBindByPosition(position =237 )
    private String attributeDate2;

    @CsvBindByPosition(position =238 )
    @JsonProperty("ATTRIBUTE_DATE3")
    @CsvBindByPosition(position =239 )
    private String attributeDate3;

    @CsvBindByPosition(position =240 )
    @JsonProperty("ATTRIBUTE_DATE4")
    @CsvBindByPosition(position =241 )
    private String attributeDate4;

    @CsvBindByPosition(position =242 )
    @JsonProperty("ATTRIBUTE_DATE5")
    @CsvBindByPosition(position =243 )
    private String attributeDate5;

    @CsvBindByPosition(position =244 )
    @JsonProperty("ATTRIBUTE_TIMESTAMP1")
    @CsvBindByPosition(position =245 )
    private String attributeTimestamp1;

    @CsvBindByPosition(position =246 )
    @JsonProperty("ATTRIBUTE_TIMESTAMP2")
    @CsvBindByPosition(position =247 )
    private String attributeTimestamp2;

    @CsvBindByPosition(position =248 )
    @JsonProperty("ATTRIBUTE_TIMESTAMP3")
    @CsvBindByPosition(position =249 )
    private String attributeTimestamp3;

    @CsvBindByPosition(position =250 )
    @JsonProperty("ATTRIBUTE_TIMESTAMP4")
    @CsvBindByPosition(position =251 )
    private String attributeTimestamp4;

    @CsvBindByPosition(position =252 )
    @JsonProperty("ATTRIBUTE_TIMESTAMP5")
    @CsvBindByPosition(position =253 )
    private String attributeTimestamp5;

    @CsvBindByPosition(position =254 )
    @JsonProperty("Consigned Flag")
    @CsvBindByPosition(position =255 )
    private String consignedFlag;

    @CsvBindByPosition(position =256 )
    @JsonProperty("Sold to Legal Entity")
    @CsvBindByPosition(position =257 )
    private String soldToLegalEntity;

    @CsvBindByPosition(position =258 )
    @JsonProperty("Consumed Quantity")
    @CsvBindByPosition(position =259 )
    private String consumedQuantity;

    @CsvBindByPosition(position =260 )
    @JsonProperty("Default Taxation Country")
    @CsvBindByPosition(position =261 )
    private String defaultTaxationCountry;

    @CsvBindByPosition(position =262 )
    @JsonProperty("Trx Business Category")
    @CsvBindByPosition(position =263 )
    private String trxBusinessCategory;

    @CsvBindByPosition(position =264 )
    @JsonProperty("Document Fiscal Classification")
    @CsvBindByPosition(position =265 )
    private String documentFiscalClassification;

    @CsvBindByPosition(position =266 )
    @JsonProperty("User Defined Fisc Class")
    @CsvBindByPosition(position =267 )
    private String userDefinedFiscClass;

    @CsvBindByPosition(position =268 )
    @JsonProperty("Product Fisc Classification")
    @CsvBindByPosition(position =269 )
    private String productFiscClassification;

    @CsvBindByPosition(position =270 )
    @JsonProperty("Intended Use")
    @CsvBindByPosition(position =271 )
    private String intendedUse;

    @CsvBindByPosition(position =272 )
    @JsonProperty("Product Category")
    @CsvBindByPosition(position =273 )
    private String productCategory;

    @CsvBindByPosition(position =274 )
    @JsonProperty("Tax Classification Code")
    @CsvBindByPosition(position =275 )
    private String taxClassificationCode;

    @CsvBindByPosition(position =276 )
    @JsonProperty("Product Type")
    @CsvBindByPosition(position =277 )
    private String productType;

    @CsvBindByPosition(position =278 )
    @JsonProperty("First Party Number")
    @CsvBindByPosition(position =279 )
    private String firstPartyNumber;

    @CsvBindByPosition(position =280 )
    @JsonProperty("Third Party Number")
    @CsvBindByPosition(position =281 )
    private String thirdPartyNumber;

    @CsvBindByPosition(position =282 )
    @JsonProperty("Tax Invoice Number")
    @CsvBindByPosition(position =283 )
    private String taxInvoiceNumber;

    @CsvBindByPosition(position =284 )
    @JsonProperty("Tax Invoice Date")
    @CsvBindByPosition(position =285 )
    private String taxInvoiceDate;

    @CsvBindByPosition(position =286 )
    @JsonProperty("Final Discharge Location Code")
    @CsvBindByPosition(position =287 )
    private String finalDischargeLocationCode;

    @CsvBindByPosition(position =288 )
    @JsonProperty("Assessable Value")
    @CsvBindByPosition(position =289 )
    private String assessableValue;

    @CsvBindByPosition(position =290 )
    @JsonProperty("Physical Return Required")
    @CsvBindByPosition(position =291 )
    private String physicalReturnRequired;

    @CsvBindByPosition(position =292 )
    @JsonProperty("External Packing Unit")
    @CsvBindByPosition(position =293 )
    private String externalPackingUnit;

    @CsvBindByPosition(position =294 )
    @JsonProperty("E-Way Bill")
    @CsvBindByPosition(position =295 )
    private String eWayBill;

    @CsvBindByPosition(position =296 )
    @JsonProperty("E-Way Bill Date")
    @CsvBindByPosition(position =297 )
    private String eWayBillDate;

    @CsvBindByPosition(position =298 )
    @JsonProperty("Recall Notice Number")
    @CsvBindByPosition(position =299 )
    private String recallNoticeNumber;

    @CsvBindByPosition(position =300 )
    @JsonProperty("Recall Notice Line Number")
    @CsvBindByPosition(position =301 )
    private String recallNoticeLineNumber;

    @CsvBindByPosition(position =302 )
    @JsonProperty("External System Transaction Reference")
    @CsvBindByPosition(position =303 )
    private String externalSystemTransactionReference;
}
