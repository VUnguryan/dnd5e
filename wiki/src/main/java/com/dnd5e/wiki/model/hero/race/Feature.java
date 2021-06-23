package com.dnd5e.wiki.model.hero.race;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.SkillType;

import lombok.Data;

@Entity
@Table(name = "race_features")
@Data
public class Feature {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@ManyToMany
	private List<Language> lanuages;
	
	@ElementCollection(targetClass = SkillType.class)
	@CollectionTable(name = "race_feature_skill_type")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private List<SkillType> skills;
}