package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.AS400Request;
import com.sal.prompt.web.dto.POHeader;
import com.sal.prompt.web.dto.response.PODistribution;
import com.sal.prompt.web.dto.response.POHeaderResponse;
import com.sal.prompt.web.dto.response.POLine;
import com.sal.prompt.web.dto.response.POLineLocation;
import com.sal.prompt.web.model.POLookupEnum;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class SupplyChainProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;

    public SupplyChainProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
        super(fbdiFormatService);
        this.fbdiFormatService = fbdiFormatService;
        this.referenceDataService = referenceDataService;
    }

    private String getRandomNumber() {
        return String.valueOf(new Random().nextInt(1000000));
    }

    @Override
    POHeaderResponse transform(AS400Request input) {
        POHeaderResponse poHeaderResponse = new POHeaderResponse();
        transformPoHeader(input.getPOHeader(), poHeaderResponse);
        transformPoLine(input.getPOHeader(), poHeaderResponse);
        transformPoLineLocation(input.getPOHeader(), poHeaderResponse);
        transformPoDistribution(input.getPOHeader(), poHeaderResponse);
        return poHeaderResponse;
    }

    private void transformPoDistribution(POHeader poHeader, POHeaderResponse poHeaderResponse) {
        for (POLine poLine : poHeaderResponse.getPoLines()) {
            for (POLineLocation poLineLocation : poLine.getPoLineLocations()) {
                List<PODistribution> poDistributions = new ArrayList<>();

                PODistribution poDistribution1 = new PODistribution();
                PODistribution poDistribution2 = new PODistribution();

                poDistribution1.setInterfaceLineLocationKey(poLineLocation.getInterfaceLineLocationKey());
                poDistribution1.setInterfaceDistributionKey(getRandomNumber());
                poDistribution2.setInterfaceDistributionKey(getRandomNumber());
                poDistribution2.setInterfaceLineLocationKey(poLineLocation.getInterfaceLineLocationKey());
                poDistributions.add(poDistribution1);
                poDistributions.add(poDistribution2);
                poLineLocation.setPoDistributions(poDistributions);
            }
        }
    }

    private void transformPoLineLocation(POHeader poHeader, POHeaderResponse poHeaderResponse) {


        for (POLine poLine : poHeaderResponse.getPoLines()) {
            List<POLineLocation> lineLocations = new ArrayList<>();
            POLineLocation poLineLocation1 = new POLineLocation();
            poLineLocation1.setInterfaceLineKey(poLine.getInterfaceLineKey());
            poLineLocation1.setInterfaceLineLocationKey(getRandomNumber());

            POLineLocation poLineLocation2 = new POLineLocation();
            poLineLocation2.setInterfaceLineKey(poLine.getInterfaceLineKey());
            poLineLocation2.setInterfaceLineLocationKey(getRandomNumber());

            lineLocations.add(poLineLocation1);
            lineLocations.add(poLineLocation2);
            poLine.setPoLineLocations(lineLocations);
        }
    }

    private void transformPoLine(POHeader poHeader, POHeaderResponse poHeaderResponse) {
        POLine poLine1 = new POLine();
        poLine1.setInterfaceHeaderKey(poHeaderResponse.getInterfaceHeaderKey());
        poLine1.setInterfaceLineKey(getRandomNumber());
        POLine poLine2 = new POLine();
        poLine2.setInterfaceHeaderKey(poHeaderResponse.getInterfaceHeaderKey());
        poLine2.setInterfaceLineKey(getRandomNumber());
        List<POLine> poLines = new ArrayList<>();
        poLines.add(poLine1);
        poLines.add(poLine2);
        poHeaderResponse.setPoLines(poLines);
    }

    private void transformPoHeader(POHeader poHeader, POHeaderResponse poHeaderResponse) {
        poHeaderResponse.setInterfaceHeaderKey(getRandomNumber());


        poHeaderResponse.setAction(referenceDataService.getPOLookupByCode(POLookupEnum.ACTION));
        poHeaderResponse.setImportSource(referenceDataService.getPOLookupByCode(POLookupEnum.IMPORT_SOURCE));
        poHeaderResponse.setApprovalAction("SUBMIT");
        poHeaderResponse.setDocumentTypeCode("STANDARD");
        poHeaderResponse.setProcurementBU("SAL");
        poHeaderResponse.setRequisitioningBU("SAL");
        poHeaderResponse.setSoldToLegalEntity("Moran Foods, LLC");
        poHeaderResponse.setBillToBU("SAL");
        poHeaderResponse.setBuyer("SAL");
        poHeaderResponse.setCurrencyCode("USD");
        poHeaderResponse.setBillToLocation("Save A Lot Food Stores");
        poHeaderResponse.setRequiredAcknowledgment("N");
        poHeaderResponse.setAttributeCategory("Supply Chain");
    }

}
