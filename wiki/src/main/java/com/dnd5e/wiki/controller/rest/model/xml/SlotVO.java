package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.dnd5e.wiki.model.hero.classes.SpellLevelDefinition;

public class SlotVO {
	@XmlAttribute
	private int level;
	@XmlElement (required = false)
	private String slots;

	public SlotVO(SpellLevelDefinition levelDefinition) {
		level = levelDefinition.getLevel();
		StringBuilder builder = new StringBuilder();

		builder.append(levelDefinition.getSlot1()).append(",");
		builder.append(levelDefinition.getSlot2()).append(",");
		builder.append(levelDefinition.getSlot3()).append(",");
		builder.append(levelDefinition.getSlot4()).append(",");
		builder.append(levelDefinition.getSlot5()).append(",");
		builder.append(levelDefinition.getSlot6()).append(",");
		builder.append(levelDefinition.getSlot7()).append(",");
		builder.append(levelDefinition.getSlot8()).append(",");
		builder.append(levelDefinition.getSlot9()).append(",");
		slots = builder.toString();
	}
}