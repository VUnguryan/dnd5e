package com.dnd5e.wiki.model.spell;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.creature.Source;
import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Data;

@Entity
@Table(name = "spells")
@Data
public class Spell {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Short level;
	private String name;
	private String englishName;
	@Enumerated(javax.persistence.EnumType.ORDINAL)
	private MagicSchool school;
	private String timeCast;
	private String distance;
	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TEXT")
	private String upperLevel;
	private Boolean ritual;
	private Boolean verbalComponent;
	private Boolean somaticComponent;
	private Boolean materialComponent;
	@Column(columnDefinition = "TEXT")
	private String additionalMaterialComponent;
	private String duration;
	private Boolean concentration;
	
	@ManyToMany
	private List<HeroClass> heroClass;
	@Enumerated(EnumType.ORDINAL)
	private Source source;
}