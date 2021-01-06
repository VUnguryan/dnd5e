package com.dnd5e.wiki.controller.rest.model.json.foundary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FSave {
    private String ability = "";
    private FDC dc;
    private String  scaling = "spell";
}
