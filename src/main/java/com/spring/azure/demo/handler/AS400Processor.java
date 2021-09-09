package com.spring.azure.demo.handler;

import com.spring.azure.demo.dto.AS400Request;
import com.spring.azure.demo.dto.POHeader;
import com.spring.azure.demo.dto.response.POHeaderResponse;

public class AS400Processor extends SourceDataProcessor {
    @Override
    POHeaderResponse transform(AS400Request input) {

        POHeaderResponse poHeader = tranformPOHeader(input.getPOHeader());

        return poHeader;
    }

    private POHeaderResponse tranformPOHeader(POHeader poHeader) {
        return POHeaderResponse.builder()
                .action("ORIGINAL")
                .importSource("SUPPLY CHAIN AS400")
                .approvalAction("SUBMIT")
                .documentTypeCode("STANDARD")
                .procurementBU("SAL")
                .requisitioningBU("SAL")
                .soldToLegalEntity("Moran Foods, LLC")
                .billToBU("SAL")
                .buyer("SAL")
                .currencyCode("USD")
                .billToLocation("Save A Lot Food Stores")
                .requiredAcknowledgment("N")
                .attributeCategory("Supply Chain")

                .interfaceHeaderKey(getInterfaceHeaderKey())
                .build();
    }

    private String getInterfaceHeaderKey() {
        return "Azure Team Internally Pass Unique Number";
    }
}
