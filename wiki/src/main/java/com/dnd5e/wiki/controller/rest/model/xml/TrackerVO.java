package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlElement;

public class TrackerVO {
	@XmlElement
	String name;
	@XmlElement
	int value;
	@XmlElement
	String rest;
}
