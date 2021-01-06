package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FRange {
    private String value;
    @JsonProperty("long")
    private String longer;
    private String units= "";
}