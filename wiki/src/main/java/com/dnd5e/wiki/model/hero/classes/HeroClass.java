package com.dnd5e.wiki.model.hero.classes;

import java.util.Comparator;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.Rest;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.HeroClassTrait;
import com.dnd5e.wiki.model.hero.Option.OptionType;
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
	private String tools;
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
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private SpellcasterType spellcasterType = SpellcasterType.NONE;

	@ElementCollection(targetClass = AbilityType.class)
	@JoinTable(name = "class_primary_abilities", joinColumns = @JoinColumn(name = "class_id"))
	@Column(name = "ability", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<AbilityType> primaryAbilities;
	
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
	private OptionType optionType;
	
	@Enumerated(EnumType.STRING)
	private Rest slotsReset;
	
	@OneToMany
	@JoinColumn(name = "class_id")
	private List<ClassPersonalization> personalizations;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;

	public String getAblativeName() {
		switch (name) {
		case "ЧАРОДЕЙ":
			return "чародеем";
		case "ИЗОБРЕТАТЕЛЬ":
			return "изобретателем";
		default:
			return name + "ом";
		}
	}

	public String getCapitalazeName() {
		return StringUtils.capitalize(name.toLowerCase());
	}

	public List<HeroClassTrait> getTraits(int level) {
		return traits.stream()
				.filter(t -> t.getLevel() == level)
				.collect(Collectors.toList());
	}
	
	public List<HeroClassTrait> getTraitsClear(int level) {
		return traits.stream()
				.filter(t -> t.getLevel() == level)
				.filter(t -> !t.isArchitype())
				.collect(Collectors.toList());
	}
	
	public List<HeroClassTrait> getTraits() {
		return traits.stream()
				.sorted(Comparator.comparingInt(HeroClassTrait::getLevel))
				.collect(Collectors.toList());
	}

	public List<ArchetypeTrait> getArhitypeTraitNames(int level){
		List<ArchetypeTrait> levelArhitypeFeats = archetypes
				.stream()
				.flatMap(a -> a.getFeats().stream())
				.filter(t-> t.getLevel() == level)
				.collect(Collectors.toList());
		return levelArhitypeFeats;
	}
}