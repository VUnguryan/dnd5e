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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.creature.Language;
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
	private String englishName;

	@Column(columnDefinition = "TEXT")
	private String toolOwnership;

	@Column(columnDefinition = "TEXT")
	private String equipmentsText;

	@ElementCollection(targetClass = SkillType.class)
	@CollectionTable(name = "background_skill_type")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private List<SkillType> skills;
	
	@Column(columnDefinition = "TEXT")
	private String otherSkills;

	private String skillName;

	@Column(columnDefinition = "TEXT")
	private String skillDescription;

	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToMany
	private List<Equipment> equipments;

	private String language;
	
	@ManyToMany
	private List<Language> languages;
	
	private int startMoney;
	
	@OneToMany
	@JoinColumn(name = "background_id")
	private List<Personalization> personalizations;
	
	@Column(columnDefinition = "TEXT")
	private String personalization;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LifeStyle lifeStyle;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;
}