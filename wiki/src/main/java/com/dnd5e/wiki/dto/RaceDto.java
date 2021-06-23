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
	private String englishName;
	private String speed;
	private String size;
	private List<FeatureDto> features;
	private List<FeatureDto> parentFeatures;

	public String abilityBonuses;
	public List<RaceDto> subRaces;
	public String hasSubRaces;
	private String book;

	public RaceDto(Race race) {
		this.id = race.getId();
		this.name = race.getCapName();
		this.englishName = race.getEnglishName();
		this.speed = race.getSpeed() + " фт.";
		if (race.getFly() != null) {
			this.speed += String.format(", летая %d фт.", race.getFly());
		}
		if (race.getClimb() != null) {
			this.speed += String.format(", лазая %d фт.", race.getClimb());
		}
		if (race.getSwim() != null) {
			this.speed += String.format(", плавая %d фт.", race.getSwim());
		}
		this.size = race.getSize().getCyrilicName();
		this.abilityBonuses= race.getAbilityBonuses();
		this.features = race.getFeatures().stream()
				.filter(f -> !f.getName().equalsIgnoreCase("увеличение характеристик."))
				.filter(f -> !f.getName().equalsIgnoreCase("размер."))
				.filter(f -> !f.getName().equalsIgnoreCase("скорость."))
				.map(FeatureDto::new)
				.collect(Collectors.toList());
		if (race.getParent()!=null) {
			this.parentFeatures = race.getParent().getFeatures().stream()
					.filter(f -> !f.getName().equalsIgnoreCase("увеличение характеристик."))
					.filter(f -> !f.getName().equalsIgnoreCase("размер."))
					.filter(f -> !f.getName().equalsIgnoreCase("скорость."))
					.map(FeatureDto::new)
					.collect(Collectors.toList()); 
		}
		this.subRaces = race.getSubRaces().stream().map(RaceDto::new).collect(Collectors.toList());
		this.hasSubRaces = subRaces.isEmpty() ? "" : "+";
		this.book = race.getBook().getName();
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
		}
	}
}