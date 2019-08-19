package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.HeroClassTrait;

public class FeatureVO {
	@XmlElement
	private String name;
	@XmlElement
	private String text;
	public FeatureVO(HeroClassTrait trait) {
		name = StringUtils.capitalize(trait.getName().toLowerCase());
		text = Conpendium.removeHtml(trait.getDescription());
	}
}
