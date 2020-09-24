package com.dnd5e.wiki.model.hero.classes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Rest;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.HeroClassTrait;
import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "classes")
public class HeroClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;

	@Column(columnDefinition = "TEXT")
	private String description; 

	@OneToMany()
	@JoinColumn(name = "hero_class_id")
	private List<SpellLevelDefinition> levelDefenitions;

	@OneToMany()
	@JoinColumn(name = "hero_class_id")
	private List<FeatureLevelDefinition> featureLevelDefenitions;
	
	private byte diceHp;

	private String armor;
	private String weapon;
	private String savingThrows;
	private String archetypeName;
	@Column(columnDefinition = "TEXT")
	private String equipment;

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
	
	@Enumerated(EnumType.STRING)
	private Rest slotsReset;
	
	@OneToMany
	@JoinColumn(name = "class_id")
	private List<ClassPersonalization> personalizations;
	
	public List<HeroClassTrait> getTraits(int level) {
		return traits.stream().filter(t -> t.getLevel() == level).collect(Collectors.toList());
	}
	
	public List<ArchetypeTrait> getArhitypeTraitNames(int level){
		return archetypes
				.stream()
				.flatMap(a -> a.getFeats().stream())
				.filter(t->t.getLevel() == level)
				.collect(Collectors.toList());
	}
}