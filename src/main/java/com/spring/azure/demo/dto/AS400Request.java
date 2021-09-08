package com.spring.azure.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AS400Request {
    @NotEmpty
    @JsonProperty("PO_Header")
    private POHeader pOHeader;
    @NotEmpty
    @JsonProperty("PO_LINES")
    private PoLines poLines;



}
