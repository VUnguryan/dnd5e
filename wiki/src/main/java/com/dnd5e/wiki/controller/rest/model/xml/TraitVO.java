package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.dnd5e.wiki.model.hero.classes.Feature;

import lombok.Getter;

@Getter
public class TraitVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String text;

	public TraitVO() {}

	public TraitVO(String name, String text) {
		this.name = name;
		this.text = text;
	}
	public TraitVO(Feature feature) {
		name = feature.getName();
		text = Conpendium.removeHtml(feature.getDescription());
	}
}