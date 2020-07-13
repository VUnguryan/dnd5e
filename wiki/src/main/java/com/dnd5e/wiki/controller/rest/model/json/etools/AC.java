package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.ArmorType;
import com.dnd5e.wiki.model.creature.Creature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ac", "from" })
public class AC {

	@JsonProperty("ac")
	private Byte ac;
	@JsonProperty("from")
	private List<String> from = null;

	public AC(Creature creature) {
		ac = creature.getAC();
		if (!creature.getArmorTypes().isEmpty()) {
			from = creature.getArmorTypes().stream().map(ArmorType::getCyrillicName).collect(Collectors.toList());
		}
	}

	@JsonProperty("ac")
	public Byte getAc() {
		return ac;
	}

	@JsonProperty("ac")
	public void setAc(Byte ac) {
		this.ac = ac;
	}

	@JsonProperty("from")
	public List<String> getFrom() {
		return from;
	}

	@JsonProperty("from")
	public void setFrom(List<String> from) {
		this.from = from;
	}

}