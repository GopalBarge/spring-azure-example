package com.sal.prompt.web.handler;

import com.sal.prompt.web.dto.SourceSystemRequest;
import com.sal.prompt.web.dto.SupplyChainRequest;
import com.sal.prompt.web.dto.POHeader;
import com.sal.prompt.web.dto.PoLines;
import com.sal.prompt.web.dto.response.*;
import com.sal.prompt.web.model.POLookupEnum;
import com.sal.prompt.web.model.Supplier;
import com.sal.prompt.web.service.FBDIFormatService;
import com.sal.prompt.web.service.ReferenceDataService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DSDProcessor extends SourceDataProcessor {

    private final ReferenceDataService referenceDataService;
    private final FBDIFormatService fbdiFormatService;

    public DSDProcessor(FBDIFormatService fbdiFormatService, ReferenceDataService referenceDataService) {
        super(fbdiFormatService);
        this.fbdiFormatService = fbdiFormatService;
        this.referenceDataService = referenceDataService;
    }


    @Override
    String getSourceSystem() {
        return null;
    }

    @Override
    String getTargetSystem() {
        return null;
    }

    @Override
    String getBatchId() {
        return null;
    }

    @Override
    protected Map<String, List> getFileDataMap(List<TargetSystemResponse> transformedData) {
        return null;
    }

    @Override
    public POHeaderResponse transform(SourceSystemRequest input) {
        return null;
    }
}