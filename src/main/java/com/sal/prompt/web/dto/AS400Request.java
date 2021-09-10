package com.sal.prompt.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AS400Request {
    @NotEmpty
    @JsonProperty("PO_Header")
    private POHeader pOHeader;




}