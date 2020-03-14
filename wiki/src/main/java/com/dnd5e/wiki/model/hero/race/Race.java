package com.dnd5e.wiki.model.hero.race;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
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

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.hero.classes.Feature;

import lombok.Data;

@Entity
@Table(name = "races")
@Data
public class Race {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	@OneToMany
	@JoinColumn(name = "race_id")
	List<Feature> features;

	@Column(columnDefinition = "TEXT")
	private String description;

	@OneToMany
	List<Language> languages;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_id")
	private Race parent;

	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	private List<Race> subRaces;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(255) default MEDIUM")
	private CreatureSize size;

	@Column(columnDefinition = "int default 30")
	private int speed;

	@OneToMany(mappedBy = "race")
	private List<RaceName> names;

	@OneToMany(mappedBy = "race")
	private List<RaceNickname> nicknames;

	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;

	public String getAbilityBonuses() {
		return features.stream().flatMap(f -> f.getAbilityBonuses().stream())
				.map(b -> b.getAbility().getShortName() + " " + String.format("%+d", b.getBonus()))
				.collect(Collectors.joining(", "));
	}

	public String getCapName() {
		return StringUtils.capitalize(name.toLowerCase());
	}

	public boolean isDarkVision() {
		return features.parallelStream().map(Feature::getName).map(String::toLowerCase)
				.anyMatch(f -> f.contains("тёмное зрение") || f.contains("темное зрение"));
	}

	public boolean isNatureArmor() {
		return features.parallelStream().map(Feature::getName).map(String::toLowerCase)
				.anyMatch(f -> f.contains("природная броня") || f.contains("природный доспех"));
	}

	public boolean isAthletics() {
		return features.parallelStream().map(Feature::getDescription).map(String::toLowerCase)
				.anyMatch(f -> f.contains("атлетик") && f.contains("владеете"));
	}

	public boolean isStealth() {
		return features.parallelStream().map(Feature::getDescription).map(String::toLowerCase)
				.anyMatch(f -> f.contains("скрытность") && f.contains("владеете"));
	}

	public List<String> getMaleNames() {
		return names.stream().filter(n -> Sex.MALE == n.getSex()).map(RaceName::getName).collect(Collectors.toList());
	}

	public List<String> getFemaleNames() {
		return names.stream().filter(n -> Sex.FEMALE == n.getSex()).map(RaceName::getName).collect(Collectors.toList());
	}
}