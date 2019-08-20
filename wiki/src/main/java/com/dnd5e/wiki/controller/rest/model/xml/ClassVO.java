package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.creature.AbilityType;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Getter;

@Getter
public class ClassVO {
	@XmlElement
	private String name;
	
	@XmlElement
	private byte hd;

	@XmlElement
	private String proficiency;

	@XmlElement
	private String armor;
	
	@XmlElement
	private String weapons;
	
	@XmlElement (required = false)
	private String tools;

	@XmlElement(name = "autolevel", required = false)
	private List<SlotVO> slots;
	
	@XmlElement(name = "autolevel", required = false)
	private List<AutolevelVO> features = new ArrayList<AutolevelVO>(20);

	public ClassVO(HeroClass hero) {
		name = StringUtils.capitalize(hero.getName().toLowerCase());
		hd = hero.getDiceHp();
		proficiency = Arrays.stream(hero.getSkills().split(","))
				.map(s -> SkillType.parse(s.trim()))
				.filter(Objects::nonNull)
				.map(SkillType::name)
				.map(s -> s.replace('_', ' '))
				.map(String::toLowerCase)
				.map(StringUtils::capitalize)
				.collect(Collectors.joining(","));
		if ("Все".equals(hero.getSkills())) {
			proficiency = Arrays.stream(SkillType.values())
					.map(SkillType::name)
					.map(s -> s.replace('_', ' '))
					.map(String::toLowerCase)
					.map(StringUtils::capitalize)
					.collect(Collectors.joining(","));
		}
		proficiency += "," + Arrays.stream(hero.getSavingThrows().split(","))
				.map(s -> AbilityType.parse(s.trim()))
				.filter(Objects::nonNull)
				.map(AbilityType::name)
				.map(s -> s.replace('_', ' '))
				.map(String::toLowerCase)
				.map(StringUtils::capitalize)
				.collect(Collectors.joining(","));
		weapons = hero.getWeapon();
		armor = hero.getArmor();
		if (hero.getId() <9) {
			slots = hero.getLevelDefenitions().stream().map(SlotVO::new).collect(Collectors.toList());
		}
		for (int level = 1; level<=20; level++) {
			features.add(getLevelFeature(level, hero));
		}
	}

	private AutolevelVO getLevelFeature(int level,HeroClass hero) {
		return new AutolevelVO(level, hero.getTraits()
				.stream()
				.filter(t -> t.getLevel() == level)
				.collect(Collectors.toList()),
				hero.getArchetypes()
				.stream()
				.flatMap(a -> a.getFeats().stream())
				.filter(t -> t.getLevel() == level).collect(Collectors.toList())
		);
	}
}
