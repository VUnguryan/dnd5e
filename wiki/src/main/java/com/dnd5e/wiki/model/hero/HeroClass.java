package com.dnd5e.wiki.model.hero;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.spell.Spell;

import lombok.Data;

@Entity
@Table(name = "classes")
@Data
public class HeroClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String boneHits;
	private String armor;
	private String weapon;
	private String savingThrows;
	private String skills;
	@ManyToMany
	private List<Spell> spells;
}