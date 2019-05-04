package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dnd5e.wiki.model.artifact.Artifact;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.hero.Equipment;
import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;

@XmlRootElement (name="compendium")
@Getter 
public class Conpendium implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String HTML_REGEXP = "<\\/?[A-Za-z]+[^>]*>";
	
	@XmlAttribute
	private int verson = 5;
	@XmlElement(name="spell")
	private List<SpellVO> spells;

	@XmlElement(name="item")
	private List<MagicItemVO> magivItems;
	
	@XmlElement(name="item")
	private List<ItemVO> items;	
	
	@XmlElement(name="monster")
	private List<CreatureVO> monsters;
	
	public Conpendium(){
		
	}
	
	public void setMonsters(List<Creature> creatures) {
		this.monsters = creatures.stream().map(c -> new CreatureVO(c)).collect(Collectors.toList());
	}
	
	public void setSpells(List<Spell> spells)
	{
		this.spells = spells.stream().map(s -> new SpellVO(s)).collect(Collectors.toList());
	}
	
	public void setMagicItems(List<Artifact> artifacts) {
		this.magivItems = artifacts.stream().map(a -> new MagicItemVO(a)).collect(Collectors.toList());
	}
	public void setItems(List<Equipment> equipments) {
		this.items = equipments.stream().map(a -> new ItemVO(a)).collect(Collectors.toList());
	}
	public static String removeHtml(String string) {
		return string == null ? ""
				: string.replaceAll(HTML_REGEXP, "").replace("&nbsp;", " ").replace("&mdash;", "")
						.replace("&ndash;", "").replace("&laquo;", "").replace("&raquo;", "").replace("&rsquo;", "")
						.replace("&bull;", "").replace("&times;", "").replace("&minus;", "-");
	}
}