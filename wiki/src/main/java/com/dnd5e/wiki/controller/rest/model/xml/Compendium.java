package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.stock.Armor;
import com.dnd5e.wiki.model.stock.Equipment;
import com.dnd5e.wiki.model.stock.Weapon;
import com.dnd5e.wiki.model.treasure.MagicThing;

import lombok.Getter;

@XmlRootElement (name="compendium")
@Getter 
public class Compendium implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String HTML_REGEXP = "<\\/?[A-Za-z]+[^>]*>";
	
	@XmlAttribute
	private int version = 5;
	@XmlElement(name="spell")
	private List<SpellVO> spells;

	@XmlElement(name="item")
	private List<MagicItemVO> magivItems;
	
	@XmlElement(name="item")
	private List<ItemVO> items;	
	
	@XmlElement(name="monster")
	private List<CreatureVO> monsters;
	
	@XmlElement(name="race")
	private List<RaceVO> races;

	@XmlElement(name="class")
	private List<ClassVO> classes;

	@XmlElement(name="feat")
	private List<FeatVO> feats;
	
	@XmlElement(name="background")
	private List<BackgroundVO> backgrounds;
	
	public Compendium(){
		
	}
	
	public void setMonsters(List<Creature> creatures) {
		this.monsters = creatures.stream().map(CreatureVO::new).collect(Collectors.toList());
	}
	
	public void setSpells(List<Spell> spells)
	{
		this.spells = spells.stream().map(SpellVO::new).collect(Collectors.toList());
	}
	
	public void setMagicItems(List<MagicThing> artifacts) {
		this.magivItems = artifacts.stream().map(MagicItemVO::new).collect(Collectors.toList());
	}

	public void setItems(List<Equipment> equipments, List<Weapon> weapons, List<Armor> armors) {
		this.items = equipments.stream().map(ItemVO::new).collect(Collectors.toList());
		this.items.addAll(weapons.stream().map(ItemVO::new).collect(Collectors.toList()));
		this.items.addAll(armors.stream().map(ItemVO::new).collect(Collectors.toList()));
	}

	public static String removeHtml(String string) {
		return string == null ? ""
				: string.replaceAll(HTML_REGEXP, " ").replace("&nbsp;", " ").replace("&mdash;", "")
						.replace("&ndash;", "").replace("&laquo;", "").replace("&raquo;", "").replace("&rsquo;", "")
						.replace("&bull;", "").replace("&times;", "").replace("&minus;", "-");
	}

	public void setRaces(List<Race> races) {
		this.races = races.stream().map(RaceVO::new).collect(Collectors.toList());;
	}

	public void setFetures(List<Trait> traits) {
		this.feats = traits.stream().map(FeatVO::new).collect(Collectors.toList());
	}

	public void setClasses(List<HeroClass> classes) {
		this.classes = classes.stream().map(ClassVO::new).collect(Collectors.toList());
	}

	public void setBsckgrounds(List<Background> backgrounds) {
		this.backgrounds = backgrounds.stream().map(BackgroundVO::new).collect(Collectors.toList());
	}
}