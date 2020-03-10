package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.HeroClassTrait;
import com.dnd5e.wiki.model.hero.classes.HeroClass;


public class AutolevelVO {
	@XmlAttribute
	private int level;
	
	@XmlAttribute (required = false)
	private String scoreImprovement;
	
	@XmlElement(name = "feature")
	private List<FeatureVO> features;
	
	@XmlElement(name = "feature")
	private List<OptionalFeatureVO> optionalFeatures;
	
	@XmlElement(name = "tracker")
	private List<TrackerVO> trackers;

	AutolevelVO(int level, List<HeroClassTrait> traits, List<ArchetypeTrait> aTraits){
		this.level = level;
		if (traits.stream().filter(f -> f.getName().equals("УВЕЛИЧЕНИЕ ХАРАКТЕРИСТИК")).findFirst().isPresent())
		{
			scoreImprovement = "YES";
		}
		features = traits
				.stream()
				.filter(f -> !f.getName().equals("УВЕЛИЧЕНИЕ ХАРАКТЕРИСТИК"))
				.map(FeatureVO::new)
				.collect(Collectors.toList());

		optionalFeatures = aTraits
				.stream()
				.map(OptionalFeatureVO::new)
				.collect(Collectors.toList());
		 
	}

	public AutolevelVO(int level, HeroClass hero) {
		this.level = level;
		List<HeroClassTrait> traits = hero.getTraits()
				.stream()
				.filter(t -> t.getLevel() == level)
				.collect(Collectors.toList());
		if (traits.stream().filter(f -> f.getName().equals("УВЕЛИЧЕНИЕ ХАРАКТЕРИСТИК")).findFirst().isPresent())
		{
			scoreImprovement = "YES";
		}
		features = traits
				.stream()
				.filter(f -> !f.getName().equals("УВЕЛИЧЕНИЕ ХАРАКТЕРИСТИК"))
				.map(FeatureVO::new)
				.collect(Collectors.toList());

		List<ArchetypeTrait> aTraits = hero.getArchetypes()
				.stream()
				.flatMap(a -> a.getFeats().stream())
				.filter(t -> t.getLevel() == level)
				.collect(Collectors.toList());
		
		optionalFeatures = aTraits
				.stream()
				.map(OptionalFeatureVO::new)
				.collect(Collectors.toList());

		//trackers = hero.getLevelDefenitions().get(level).getTrackers();
	}
}