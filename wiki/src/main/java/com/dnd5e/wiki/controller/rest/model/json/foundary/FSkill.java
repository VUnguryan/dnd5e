package com.dnd5e.wiki.controller.rest.model.json.foundary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FSkill {
    private byte value;
    private String ability;
    private byte bonus;
    private byte mod;
    private byte passive;
    private byte prof;
    private byte total;
}