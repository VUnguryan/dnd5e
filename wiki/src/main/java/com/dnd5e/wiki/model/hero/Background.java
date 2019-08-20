package com.dnd5e.wiki.model.hero;

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

import com.dnd5e.wiki.model.Source;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.stock.Equipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "backgrounds")
public class Background {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@ElementCollection(targetClass = SkillType.class)
	@CollectionTable(name = "background_skill_type")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private List<SkillType> skills;

	@ManyToMany
	private List<Equipment> equipments;

	private String skillName;
	@Column(columnDefinition = "TEXT")
	private String skillDescription;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Source source;
}