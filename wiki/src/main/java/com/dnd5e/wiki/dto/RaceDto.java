package com.dnd5e.wiki.dto;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.hero.AbilityBonus;
import com.dnd5e.wiki.model.hero.race.Feature;
import com.dnd5e.wiki.model.hero.race.Race;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RaceDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String parentName;
	private String speed;
	private String size;
	private List<FeatureDto> features;
	private List<FeatureDto> parentFeatures;

	public String abilityBonuses = "";
	public List<RaceDto> subRaces;
	public String hasSubRaces;

	public RaceDto(Race race) {
		this.id = race.getId();
		this.name = race.getCapName();
		if (race.getParent() != null) {
			this.parentName =race.getParent().getCapName(); 
			this.name = name.replace(parentName + " ", "");
		}
		this.speed = race.getSpeed() + " фт.";
		this.size = race.getSize().getCyrilicName();
		if (race.getParent() !=null && race.getSubRaces().isEmpty()) {
			this.parentFeatures = race.getParent().getFeatures().stream()
					.filter(f -> !f.getName().equalsIgnoreCase("мировоззрение."))
					.filter(f -> !f.getName().equalsIgnoreCase("размер."))
					.filter(f -> !f.getName().equalsIgnoreCase("возраст."))
					.filter(f -> !f.getName().equalsIgnoreCase("скорость."))
					.filter(f -> !f.getName().equalsIgnoreCase("языки."))
					.map(FeatureDto::new)
					.collect(Collectors.toList());;
			this.abilityBonuses+= race.getParent().getFullNameAbilityBonuses();
		}

		this.abilityBonuses+= race.getFullNameAbilityBonuses();
		this.features = race.getFeatures().stream()
				.filter(f -> !f.getName().equalsIgnoreCase("мировоззрение."))
				.filter(f -> !f.getName().equalsIgnoreCase("размер."))
				.filter(f -> !f.getName().equalsIgnoreCase("возраст."))
				.filter(f -> !f.getName().equalsIgnoreCase("скорость."))
				.filter(f -> !f.getName().equalsIgnoreCase("языки."))
				.map(FeatureDto::new)
				.collect(Collectors.toList());
		this.subRaces = race.getSubRaces().stream().map(RaceDto::new).collect(Collectors.toList());
		this.hasSubRaces = subRaces.isEmpty() ? "" : "+";
	}

	@NoArgsConstructor
	@Getter
	@Setter
	class FeatureDto implements Serializable {
		private static final long serialVersionUID = 1L;

		private String name;
		private String description;
		private List<String> abilityOptions;
		private int choice = 0;
		FeatureDto (Feature feature){
			this.name = feature.getName();
			this.description = feature.getDescription();
			if (feature.getAbilityBonuses().stream().anyMatch(bonus -> bonus.getAbility() == AbilityType.CHOICE)){
				choice = 1;
			}
			if (feature.getAbilityBonuses().stream().anyMatch(bonus -> bonus.getAbility() == AbilityType.CHOICE_UNIQUE)){
				choice = 2;
			}
			if (choice > 0) {
				Set<AbilityType> abilities = EnumSet.range(AbilityType.STRENGTH, AbilityType.CHARISMA);
				Set<AbilityType> existAbility = feature.getAbilityBonuses().stream().map(AbilityBonus::getAbility).collect(Collectors.toSet());
				abilities.removeAll(existAbility);
				abilityOptions = abilities.stream().map(AbilityType::getCyrilicName).collect(Collectors.toList()); 
			}
		}
	}
}