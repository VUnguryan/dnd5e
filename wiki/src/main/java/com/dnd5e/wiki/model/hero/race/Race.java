package com.dnd5e.wiki.model.hero.race;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.hero.AbilityBonus;
import com.dnd5e.wiki.model.hero.race.RaceNickname.NicknameType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "races")
public class Race implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;
	private Integer minAge;
	private Integer maxAge;

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
	private Integer fly;
	private Integer climb;
	private Integer swim;

	@OneToMany(mappedBy = "race")
	private List<RaceName> names;

	@OneToMany(mappedBy = "race")
	private List<RaceNickname> nicknames;
	private boolean view = true;
	
	@OneToMany
	@JoinColumn(name = "race_id")
	private List<AbilityBonus> bonuses;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;

	public String getFullName() {
		if (parent != null) {
			String parentName = parent.getName();
			return name.contains(parentName) ? name : name + " " + parentName;
		}
		return name;
	}

	public String getAbilityBonuses() {
		if (bonuses.size() == 6) {
			return "+1 к каждой характеристике";
		}
		return bonuses.stream()
				.map(b -> b.getAbility() == AbilityType.CHOICE_UNIQUE || b.getAbility() == AbilityType.CHOICE
						? String.format("%s %+d", b.getAbility().getCyrilicName(), b.getBonus())
						: String.format("%s %+d", b.getAbility().getShortName(), b.getBonus()))
				.collect(Collectors.joining(", "));
	}

	public List<AbilityBonus> getAbilityValueBonuses() {
		return bonuses;
	}
	
	public String getFullNameAbilityBonuses() {
		if (bonuses.size() == 6) {
			return "+1 к каждой характеристике";
		}
		return bonuses.stream()
				.map(b -> String.format("%s %+d", b.getAbility().getCyrilicName(), b.getBonus()))
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

	public boolean isResistenceFire() {
		return features.parallelStream().map(Feature::getDescription).map(String::toLowerCase)
				.anyMatch(f -> f.contains("сопротивление") && (f.contains("огнём") || f.contains("огню")));
	}

	public boolean isResistencePoison() {
		return features.parallelStream().map(Feature::getDescription).map(String::toLowerCase)
				.anyMatch(f -> (f.contains("урону ядом")));
	}
	public boolean isResistenceCold() {
		return features.parallelStream().map(Feature::getDescription).map(String::toLowerCase)
				.anyMatch(f -> (f.contains("урону холодом")));
	}

	public Map<Sex, Set<String>> getNames() {
		return names.stream().collect(Collectors.groupingBy(RaceName::getSex,
				Collectors.mapping(RaceName::getName, Collectors.toCollection(TreeSet::new))));
	}
	
	public List<RaceNickname> getAllNicknames() {
		return Stream.concat(nicknames.stream(), parent == null ? Stream.empty() : parent.getNicknames().stream()).collect(Collectors.toList());
	}
	
	public Map<Sex, Set<String>> getAllNames() {
		return Stream.concat(names.stream(), parent == null ? Stream.empty() : parent.names.stream())
				.collect(Collectors.groupingBy(RaceName::getSex,
						Collectors.mapping(RaceName::getName, Collectors.toCollection(TreeSet::new))));
	}
	
	public Map<NicknameType, Set<String>> getNicknamesGroup() {
		return nicknames.stream()
				.collect(Collectors.groupingBy(RaceNickname::getType,
						Collectors.mapping(RaceNickname::getName, Collectors.toCollection(TreeSet::new))));
	}
}