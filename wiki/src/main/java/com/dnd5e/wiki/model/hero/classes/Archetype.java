package com.dnd5e.wiki.model.hero.classes;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
import com.dnd5e.wiki.model.spell.Spell;

import lombok.Data;

@Entity
@Table(name = "archetypes")
@Data
public class Archetype {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	private byte level;
	
	@ManyToOne(targetEntity = HeroClass.class)
	private HeroClass heroClass;

	@OneToMany
	@JoinColumn(name = "archetype_id")
	private List<ArchetypeTrait> feats;
	
	@OneToMany
	@JoinColumn(name = "archetype_id")
	private List<ArchetypeSpell> spells;

	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;
	
	public Map<Integer, List<Spell>> getLevelSpells(){
		return spells.stream()
				.collect(Collectors.groupingBy(ArchetypeSpell::getLevel, TreeMap::new,
						Collectors.mapping(ArchetypeSpell::getSpell, Collectors.toList())));
	}
}