package com.dnd5e.wiki.model.hero.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "archetype_spell")
public class ArchetypeSpell {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int level;
	
	@OneToOne
	private Spell spell;
}