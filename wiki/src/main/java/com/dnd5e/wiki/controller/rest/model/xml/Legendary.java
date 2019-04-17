package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class Legendary implements Serializable{
	@XmlElement
	private String name;
	@XmlElement
	private String text;
	public Legendary() {
		
	}
	
	public Legendary(String name, String text) {
		this.name = name;
		this.text = text;
	}

	public String getName() {
		return name;
	}
	public String getText() {
		return text;
	}
	
}
