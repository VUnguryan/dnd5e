package com.dnd5e.wiki.model.hero.madness;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "hero_madness")
/**
 * Безумие персонажей
 */
public class Madness {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = true)
	private String other;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private MadnessType madnessType;
}