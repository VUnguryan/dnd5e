package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlElement;

public class ItemVO {
	private static final String HTML_REGEXP = "<\\/?[A-Za-z]+[^>]*>";
	
	@XmlElement
	private String name;
	@XmlElement
	private String type;
	@XmlElement
	private String weight;
	public ItemVO(){
		
	}
}
