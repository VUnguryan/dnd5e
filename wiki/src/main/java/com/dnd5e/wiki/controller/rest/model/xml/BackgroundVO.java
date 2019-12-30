package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Background;

public class BackgroundVO {
	@XmlElement
	private String name;
	@XmlElement(name = "text")
	private String descriptions;
	
	@XmlElement(name = "text")
	private String source;

	@XmlElement
	private String proficiency;

	@XmlElement
	private TraitVO trait;

	public BackgroundVO(Background background) {
		name = StringUtils.capitalize(background.getName().toLowerCase()).trim();
		descriptions = Compendium.removeHtml(background.getDescription()).trim();
		proficiency = background.getSkills().stream()
			.map(SkillType::name)
			.map(s -> s.replace('_', ' '))
			.map(String::toLowerCase)
			.map(StringUtils::capitalize)
			.collect(Collectors.joining(","));
		trait = new TraitVO(background.getSkillName(), background.getSkillDescription());
	}
}