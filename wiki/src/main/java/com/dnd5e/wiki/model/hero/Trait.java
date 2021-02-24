package com.dnd5e.wiki.model.hero;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.creature.SkillType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "traits")
public class Trait {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String englishName;
	
	private String requirement;
	
	@Column(columnDefinition = "TEXT")
	private String description;

	@ElementCollection(targetClass = AbilityType.class)
	@JoinTable(name = "trait_abilities", joinColumns = @JoinColumn(name = "trait_id"))
	@Column(name = "ability", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<AbilityType> abilities;
	
	@ElementCollection(targetClass = SkillType.class)
	@JoinTable(name = "trait_skills", joinColumns = @JoinColumn(name = "trait_id"))
	@Column(name = "skill", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<SkillType> skills;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
}