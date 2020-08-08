package com.dnd5e.wiki.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.ArmorType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.Skill;
import com.dnd5e.wiki.model.creature.State;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatureDto {
	private int id; 
	private String name;
	private String englishName;
	private String type;
	private String size;
	private String alignment;
	private String book;
	private String exp;
	private String habitates;
	
	private int ac;
	private String armorType;
	private String hp;
	private String speed;
	
	private String languages;
	
	private String str;
	private String dex;
	private String con;
	private String inte;
	private String wiz;
	private String chr;
	private String savingThrows;
	private String skills;
	private String vulnerabilityDamages;
	private String resistanceDamages;
	private Object immunityDamages;
	private Object immunityStates;
	private String feelings;
	private List<CreatureTraitDto> feets;
	private List<Action> actions;
	private List<Action> reactions;
	private List<Action> legendaryActions;
	private String legendary;
	private int expa;
	private String race;
	
	public CreatureDto(Creature creature) {
		id = creature.getId();
		exp = creature.getChallengeRating();
		expa = creature.getExp();;
		name = creature.getName();
		englishName = creature.getEnglishName();
		size = creature.getSize().getSizeName(creature.getType());
		type = creature.getType().getCyrilicName();
		race = creature.getRaceName();
		alignment = creature.getAlignment().getCyrilicName();
		habitates = creature.getHabitates().stream().map(HabitatType::getName).collect(Collectors.joining(", "));
		
		ac = creature.getAC();
		armorType = creature.getArmorTypes().isEmpty() ? null : creature.getArmorTypes().stream().map(ArmorType::getCyrillicName).collect(Collectors.joining(", "));
		book = creature.getBook().getName();
		hp = creature.getHp();
		speed = "" + creature.getSpeed() + " фт." + (creature.getFlySpeed() == null ? "" : ", летая " + creature.getFlySpeed() + " фт.") 
				+ (creature.getSwimmingSpped() == null ? "" : ", плавая " + creature.getSwimmingSpped() + " фт.")
				+ (creature.getDiggingSpeed() == null ? "" : ", копая " + creature.getDiggingSpeed() + " фт.")
				+ (creature.getClimbingSpeed() == null ? "" : ", лазая " + creature.getClimbingSpeed() + " фт.");
		
		str = creature.strengthText();
		dex = creature.dexterityText();
		con = creature.constitutionText();
		inte = creature.intellectText();
		wiz = creature.wizdomText();
		chr = creature.charismaText();
		
		languages = creature.getLanguages().stream().map(Language::getName).collect(Collectors.joining(", "));
		savingThrows = creature.getSavingThrows().isEmpty() ? null : creature.getSavingThrows().stream()
				.map(s -> String.format("%s %+d", s.getAbility().getShortName(), s.getBonus()))
				.collect(Collectors.joining(", "));
		skills = creature.getSkills().isEmpty() ? null : creature.getSkills().stream().map(Skill::getCyrilicText).collect(Collectors.joining(", "));
		
		vulnerabilityDamages = creature.getVulnerabilityDamages().isEmpty() ? null : creature.getVulnerabilityDamages().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		resistanceDamages =	creature.getResistanceDamages().isEmpty() ? null : creature.getResistanceDamages().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		immunityDamages = creature.getImmunityDamages().isEmpty() ? null : creature.getImmunityDamages().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		immunityStates = creature.getImmunityStates().isEmpty() ? null : creature.getImmunityStates().stream().map(State::getCyrilicName).collect(Collectors.joining(", "));
		feelings = (creature.getBlindsight() == null ? "" : String.format("слепое зрение %d фт.", creature.getBlindsight()) + (creature.getBlindsightRadius() == null ? "" : "(слеп за пределами этого радиуса)") + ", ") 
				+ (creature.getDarkvision() == null ? "" : String.format("тёмное зрение %d фт., ", creature.getDarkvision())) 
				+ (creature.getTrysight() == null ? "" : String.format("истинное зрение %d фт., ", creature.getTrysight()))
				+ (creature.getVibration() == null ? "" : String.format("чувство вибрации %d фт., ", creature.getVibration()))
				+ "пассивная Внимательность " + creature.getPassivePerception();

		
		feets = creature.getFeats().stream().map(CreatureTraitDto::new).collect(Collectors.toList());
		actions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION).collect(Collectors.toList());
		reactions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION).collect(Collectors.toList());
		legendary = creature.getLegendary() == null ? String.format("%1$s может совершить 3 легендарных действия, выбирая из представленных ниже вариантов. За один раз можно использовать только одно легендарное действие, и только в конце хода другого существа. %1$s восстанавливает использованные легендарные действия в начале своего хода.", name) : creature.getLegendary();
		legendaryActions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY).collect(Collectors.toList());
	}
}