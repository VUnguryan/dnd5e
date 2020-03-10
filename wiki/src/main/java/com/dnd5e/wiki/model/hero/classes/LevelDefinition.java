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

@Entity
@Table(name = "levelDefinitions")
@Getter
@Setter
@NoArgsConstructor
public class LevelDefinition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private byte level;
	private byte masteryBonus;
	private byte spells;
	
	private byte slot0;
	private byte slot1;
	private byte slot2;
	private byte slot3;
	private byte slot4;
	private byte slot5;
	private byte slot6;
	private byte slot7;
	private byte slot8;
	private byte slot9;
	
	@OneToMany
	@JoinColumn(name = "level_def_id")

	private List<Tracker> trackers;
	@ManyToOne(targetEntity = HeroClass.class)
	private HeroClass heroClass;
}