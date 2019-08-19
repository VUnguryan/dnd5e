package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Getter;

@Getter
public class ClassVO {
	@XmlElement
	private String name;

	@XmlElement(name = "autolevel", required = false)
	private List<SlotVO> slots;
	@XmlElement(name = "autolevel", required = false)
	private List<AutolevelVO> features = new ArrayList<AutolevelVO>(20);

	public ClassVO(HeroClass hero) {
		name = StringUtils.capitalize(hero.getName().toLowerCase());
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
