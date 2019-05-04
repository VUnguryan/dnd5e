package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.artifact.Artifact;

public class MagicItemVO {
	@XmlElement
	private String name;
	@XmlElement(required = false)
	private String type;
	@XmlElement(required = false)
	private String weight;
	@XmlElement(required = false)
	private String magic;
	@XmlElement
	private String rarity;
	@XmlElement(name="text")
	private String rarityText;
	@XmlElement(name="text", required=false)
	private String requiresAttunement;
	@XmlElement()
	private String text;
	
	public MagicItemVO(){
		
	}

	public MagicItemVO(Artifact art) {
		this.name = StringUtils.capitalize(art.getName().toLowerCase());
		this.magic = "1";
		this.rarity = StringUtils.capitalize(art.getRarity().name().toLowerCase().replace("_", " "));
		this.rarityText = "Редкость: " + art.getRarity().getCyrilicName();
		if (art.getCustomization()!= null && art.getCustomization())	{
			this.requiresAttunement = "Требуется настройка";
			if (!art.getCustClasses().isEmpty()) {
				requiresAttunement+=": ";
				requiresAttunement += art.getCustClasses()
						.stream()
						.map(c -> StringUtils.capitalize(c.getName().toLowerCase()))
						.collect(Collectors.joining(", "));
			}
		}
		this.text = Conpendium.removeHtml(art.getDescription());
		switch (art.getType())
		{
		case ARMOR:
			this.type = "LA";
			break;
		case POTION:
			this.type = "P";
			break;
		case RING:
			this.type = "RG";
			break;
		case ROD:
			this.type = "RD";
			break;
		case SCROLL:
			this.type = "SC";
			break;
		case STAFF:
			this.type = "ST";
			break;
		case SUBJECT:
			this.type = "W";
			break;
		case WAND:
			this.type = "WD";
			break;
		case WEAPON:
			this.type = "M";
			break;
		
		}
	}
}
