package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.List;

import com.dnd5e.wiki.model.creature.SavingThrow;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "str", "dex", "con", "int", "wis", "chr" })
public class Save {
	@JsonProperty("str")
	private String str;
	@JsonProperty("dex")
	private String dex;
	@JsonProperty("con")
	private String con;
	@JsonProperty("int")
	private String _int;
	@JsonProperty("wis")
	private String wis;
	@JsonProperty("chr")
	private String chr;

	public Save(List<SavingThrow> savingThrows) {
		for (SavingThrow savingThrow : savingThrows) {
			switch (savingThrow.getAbility()) {
			case STRENGTH:
				str = String.format("%+d", savingThrow.getBonus());
				break;
			case DEXTERITY:
				dex = String.format("%+d", savingThrow.getBonus());
				break;
			case CONSTITUTION:
				con = String.format("%+d", savingThrow.getBonus());
				break;
			case INTELLIGENCE:
				_int = String.format("%+d", savingThrow.getBonus());
				break;
			case WISDOM:
				wis = String.format("%+d", savingThrow.getBonus());
				break;
			case CHARISMA:
				chr = String.format("%+d", savingThrow.getBonus());
				break;
			default:
				break;
			}
		}
	}
}