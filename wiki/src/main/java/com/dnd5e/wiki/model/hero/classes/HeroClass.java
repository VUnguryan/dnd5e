package com.dnd5e.wiki.model.hero.classes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.dnd5e.wiki.model.creature.AbilityType;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.HeroClassTrait;
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

	@OneToMany()
	@JoinColumn(name = "hero_class_id")
	private List<LevelDefinition> levelDefenitions;

	private String boneHits;
	private byte diceHp;

	private String armor;
	private String weapon;
	private String savingThrows;
	private String skills;
	private String archetypeName;

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("level")
	private List<Spell> spells;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private AbilityType spellAbility;

	@OneToMany()
	@JoinColumn(name = "hero_class_id")
	private List<HeroClassTrait> traits;

	private int enabledArhitypeLevel;

	@OneToMany()
	@JoinColumn(name = "class_id")
	private List<Archetype> archetypes;

	private short skillAvailableCount;

	@ElementCollection(targetClass = SkillType.class)
	@JoinTable(name = "class_available_skills", joinColumns = @JoinColumn(name = "class_id"))
	@Column(name = "skill", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<SkillType> availableSkills;
}