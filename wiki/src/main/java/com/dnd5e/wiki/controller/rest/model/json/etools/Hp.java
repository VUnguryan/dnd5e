package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.HashMap;
import java.util.Map;

import com.dnd5e.wiki.model.creature.Creature;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "average", "formula" })
public class Hp {

	@JsonProperty("average")
	private Short average;
	@JsonProperty("formula")
	private String formula;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Hp(Creature creature) {
		average = creature.getAverageHp();
		formula = creature.getCountDiceHp()  + creature.getDiceHp().toString() + (creature.getBonusHP() == null ? "" : " + " + creature.getBonusHP());
	}

	@JsonProperty("average")
	public Short getAverage() {
		return average;
	}

	@JsonProperty("average")
	public void setAverage(Short average) {
		this.average = average;
	}

	@JsonProperty("formula")
	public String getFormula() {
		return formula;
	}

	@JsonProperty("formula")
	public void setFormula(String formula) {
		this.formula = formula;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}