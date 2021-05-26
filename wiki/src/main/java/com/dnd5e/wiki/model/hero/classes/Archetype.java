package com.dnd5e.wiki.model.hero.classes;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.Option.OptionType;
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
	private String genitiveName;

	private String englishName;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	private byte level;
	
	@ManyToOne(targetEntity = HeroClass.class)
	private HeroClass heroClass;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private SpellcasterType spellcasterType;
	
	@OneToMany()
	@JoinColumn(name = "archetype_id")
	private List<SpellLevelDefinition> levelDefenitions;
	
	@OneToMany()
	@JoinColumn(name = "archetype_id")
	private List<FeatureLevelDefinition> featureLevelDefenitions;

	@OneToMany
	@JoinColumn(name = "archetype_id")
	private List<ArchetypeTrait> feats;
	
	@OneToMany
	@JoinColumn(name = "archetype_id")
	private List<ArchetypeSpell> spells;

	@Enumerated(EnumType.STRING)
	private OptionType optionType;

	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;
	
	public Map<Integer, List<Spell>> getLevelSpells(){
		return spells.stream().filter(s -> s.getLevel() > 0)
				.collect(Collectors.groupingBy(ArchetypeSpell::getLevel, TreeMap::new,
						Collectors.mapping(ArchetypeSpell::getSpell, Collectors.toList())));
	}
	
	public boolean isOfficial() {
		return book.getType() != null && book.getType() == TypeBook.OFFICAL;  
	}

	public boolean isSetting() {
		return book.getType() != null && book.getType() == TypeBook.SETTING;
	}
}