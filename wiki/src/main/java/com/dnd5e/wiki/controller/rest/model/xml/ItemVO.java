package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.stock.Currency;
import com.dnd5e.wiki.model.stock.Equipment;

public class ItemVO {
	@XmlElement
	private String name;
	@XmlElement(required = false)
	private String type;
	@XmlElement()
	private String text;
	@XmlElement(required = false)
	private float weight;
	@XmlElement()
	private float value;

	public ItemVO() {

	}

	public ItemVO(Equipment equipment) {
		this.name = StringUtils.capitalize(equipment.getName().toLowerCase());
		this.text = Conpendium.removeHtml(equipment.getDescription());
		this.type = "AG";
		this.value = Math.round(100*Currency.GM.convert(equipment.getCurrency(), equipment.getCost()))/100f;
		this.weight = equipment.getWeight(); 
	}
}
