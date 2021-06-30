package com.dnd5e.wiki.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.HeroClassTrait;
import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ClassDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String englishName;
	private byte diceHp;
	private String primaryAbilities;
	private String savingThrows;
	
	private String armor;
	private String weapon;
	private String tools;
	
	private short skillTrainingCount;
	private List<SkillType> availableSkills;
	
	private List<ClassTraitDto> traits;
	private String book;

	public ClassDto(HeroClass heroClass) {
		this.id = heroClass.getId();
		this.name = heroClass.getName();
		this.englishName = heroClass.getEnglishName();
		this.diceHp = heroClass.getDiceHp();
		this.primaryAbilities = heroClass.getPrimaryAbilities().stream()
				.map(AbilityType::getCyrilicName)
				.collect(Collectors.joining(" или "));
		this.savingThrows = heroClass.getSavingThrows();
		this.armor = heroClass.getArmor();
		this.weapon = heroClass.getWeapon();
		this.skillTrainingCount = heroClass.getSkillAvailableCount();

		this.availableSkills = heroClass.getAvailableSkills().stream().collect(Collectors.toList());

		this.traits = heroClass.getTraits().stream()
				.filter(t -> t.getDescription() != null)
				.map(ClassTraitDto::new)
				.sorted(Comparator.comparingInt(ClassTraitDto::getLevel))
				.collect(Collectors.toList());
		book = heroClass.getBook().getName();
	}

	@NoArgsConstructor
	@Getter
	@Setter
	class ClassTraitDto implements Serializable {
		private static final long serialVersionUID = 1L;

		private String name;
		private byte level;
		private String description;

		ClassTraitDto (HeroClassTrait trait){
			this.name = trait.getName();
			this.level = trait.getLevel();
			this.description = trait.getDescription();
		}
	}
}