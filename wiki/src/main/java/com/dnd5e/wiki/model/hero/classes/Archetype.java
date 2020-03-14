package com.dnd5e.wiki.model.hero.classes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.hero.ArchetypeTrait;

import lombok.Data;

@Entity
@Table(name = "archetypes")
@Data
public class Archetype {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToOne(targetEntity = HeroClass.class)
	private HeroClass heroClass;

	@OneToMany
	@JoinColumn(name = "archetype_id")
	private List<ArchetypeTrait> feats;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
}