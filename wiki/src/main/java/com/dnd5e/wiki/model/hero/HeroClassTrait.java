package com.dnd5e.wiki.model.hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Data;

@Entity
@Table(name = "hero_class_traits")
@Data
public class HeroClassTrait {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int level;
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@ManyToOne(targetEntity = HeroClass.class)
	private HeroClass heroClass;
}
