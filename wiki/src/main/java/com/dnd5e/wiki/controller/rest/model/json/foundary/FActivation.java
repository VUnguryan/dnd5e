package com.dnd5e.wiki.controller.rest.model.json.foundary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FActivation {
    private String type = "";
    private byte cost;
    private String condition = "";
}
