package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.HeroClassTrait;


public class AutolevelVO {
	@XmlAttribute
	private int level;
	@XmlElement(name = "feature")
	private List<FeatureVO> features;
	@XmlElement(name = "feature")
	private List<OptionalFeatureVO> optionalFeatures;
	AutolevelVO(int level, List<HeroClassTrait> traits, List<ArchetypeTrait> aTraits){
		this.level = level;
		features = traits.stream().map(FeatureVO::new).collect(Collectors.toList());
		optionalFeatures = aTraits.stream().map(OptionalFeatureVO::new).collect(Collectors.toList());
	}
}
