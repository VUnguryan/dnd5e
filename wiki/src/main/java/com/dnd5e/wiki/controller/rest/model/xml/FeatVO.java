package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.Trait;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FeatVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String text;
	@XmlElement
	private String prerequisite;
	
	public FeatVO(Trait trait) {
		name = StringUtils.capitalize(trait.getName().toLowerCase()).trim();
		text = Compendium.removeHtml(trait.getDescription()).trim();
		prerequisite = trait.getRequirement();
	}
}