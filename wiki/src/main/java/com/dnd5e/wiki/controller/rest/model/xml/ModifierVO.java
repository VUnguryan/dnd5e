package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModifierVO {
	@XmlAttribute
	private String category;
	
	@XmlValue
	private String text;
	enum Type {
		bonus
	}
}
