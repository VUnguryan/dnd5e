package com.dnd5e.wiki.controller.rest.model.json.foundary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FConsume {
    private String type = "";
    private String target;
    private String amount;
}