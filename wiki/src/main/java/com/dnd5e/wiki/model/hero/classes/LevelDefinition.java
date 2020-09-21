package com.dnd5e.wiki.model.hero.classes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "levelDefinitions")
public class LevelDefinition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Byte level;
	private Byte masteryBonus;
	private Byte spells;
	
	private Byte slot0;
	private Byte slot1;
	private Byte slot2;
	private Byte slot3;
	private Byte slot4;
	private Byte slot5;
	private Byte slot6;
	private Byte slot7;
	private Byte slot8;
	private Byte slot9;
	
	@OneToMany
	@JoinColumn(name = "level_def_id")
	private List<Tracker> trackers;
	
	@ManyToOne(targetEntity = HeroClass.class)
	private HeroClass heroClass;
}