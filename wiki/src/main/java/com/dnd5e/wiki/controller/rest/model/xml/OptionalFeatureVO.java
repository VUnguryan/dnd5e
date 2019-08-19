package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.ArchetypeTrait;

public class OptionalFeatureVO {
	@XmlAttribute
	private String optional="YES";
	@XmlElement
	private String name;
	@XmlElement
	private String text;
	public OptionalFeatureVO(ArchetypeTrait trait) {
		
		name = StringUtils.capitalize(trait.getArchetype().getName().toLowerCase()) + ": "+StringUtils.capitalize(trait.getName().toLowerCase());
		text = Conpendium.removeHtml(trait.getDescription());
	}
}
