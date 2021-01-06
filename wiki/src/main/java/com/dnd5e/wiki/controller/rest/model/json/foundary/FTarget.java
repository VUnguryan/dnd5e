package com.dnd5e.wiki.controller.rest.model.json.foundary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FTarget {
    private String value;
    private String width;
    private String units= "";
    private String type = "";
}