package com.sal.prompt.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Lookup {

    @JsonProperty("LookupType")
    private String lookupType;
    @JsonProperty("LookupCode")
    private String lookupCode;
    @JsonProperty("Description")
    private String description;
}
