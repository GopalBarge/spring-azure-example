package com.spring.azure.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AS400Request {
    @JsonProperty("PO_Header")
    private POHeader pOHeader;
    @JsonProperty("PO_LINES")
    private PoLines poLines;

}
