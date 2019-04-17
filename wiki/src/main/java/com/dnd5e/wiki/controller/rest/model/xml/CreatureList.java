package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.dnd5e.wiki.model.creature.Creature;

@XmlRootElement (name="compendium")
public class CreatureList implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlAttribute
	private int verson = 5;
	private List<CreatureVO> monster;
	public CreatureList() {
		
	}
	public CreatureList(List<Creature> creatures) {
		this.monster = creatures.stream().map(c -> new CreatureVO(c)).collect(Collectors.toList());
		
	}
	public List<CreatureVO> getMonster() {
		return monster;
	}
	public void setMonster(List<CreatureVO> monster) {
		this.monster = monster;
	}

}
