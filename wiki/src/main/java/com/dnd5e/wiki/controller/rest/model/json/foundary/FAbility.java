package com.dnd5e.wiki.controller.rest.model.json.foundary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAbility {
	 private byte value;
	 private byte proficient;
	 private byte min = 3;
	 private byte mod;
	 private byte save;
	 private byte prof;
	 private byte saveBonus;
	 private byte checkBonus;
	 private byte dc;
}