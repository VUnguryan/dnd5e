package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;

@XmlRootElement (name="compendium")
@Getter 
public class CreatureList implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlAttribute
	private int verson = 5;
	
	@XmlElement(name="monster")
	private List<CreatureVO> monster;
	
	@XmlElement(name="spell")
	private List<SpellVO> spell;

	public CreatureList(){
		
	}
	public void setMonsters(List<Creature> creatures) {
		this.monster = creatures.stream().map(c -> new CreatureVO(c)).collect(Collectors.toList());
	}
	public void setSpells(List<Spell> spells)
	{
		this.spell = spells.stream().map(s -> new SpellVO(s)).collect(Collectors.toList());
	}
}