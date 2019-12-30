package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.HeroClassTrait;

public class FeatureVO {
	@XmlElement
	private String name;
	@XmlElement
	private List<String> text;

	public FeatureVO(HeroClassTrait trait) {
		name = StringUtils.capitalize(trait.getName().toLowerCase());
		if (trait.getDescription() != null) {
			text = Arrays.stream(trait.getDescription().split("<strong>"))
					.filter(Objects::nonNull)
					.filter(s -> !s.isEmpty())
					.map(Compendium::removeHtml)
					.map(String::trim)
					.collect(Collectors.toList()); 
		}
	}
}