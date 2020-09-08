package com.dnd5e.wiki.controller.rest.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.model.api.BackgroundDto;
import com.dnd5e.wiki.controller.rest.model.api.ClassDto;
import com.dnd5e.wiki.controller.rest.model.api.ClassInfo;
import com.dnd5e.wiki.controller.rest.model.api.PersonalizationDto;
import com.dnd5e.wiki.controller.rest.model.api.SpellDto;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.repository.BackgroundRepository;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.SpellRepository;

@RestController()
@RequestMapping("/api")
public class ApiRestController {
	@Autowired
	private BackgroundRepository backgroundRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private SpellRepository spellRepository;

	@GetMapping("/spells")
	public List<SpellDto> getSpells(){
		return spellRepository.findAll().stream().map(SpellDto::new).collect(Collectors.toList());
	}

	@GetMapping("/classes")
	public List<ClassDto> getClasses(){
		return classRepository.findAll().stream().map(ClassDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/classes/{id}")
	public ClassInfo getClass(@PathVariable Integer id){
		return new ClassInfo(classRepository.findById(id).orElseGet(() -> new HeroClass()));
	}
	
	@GetMapping("/backgrounds")
	public List<BackgroundDto> getBackgrounds(){
		return backgroundRepository.findAll().stream().map(BackgroundDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/backgrounds/{id}/personalization")
	public List<PersonalizationDto> getBackground(@PathVariable Integer id){
		return backgroundRepository.findById(id).orElseGet(() -> new Background()).getPersonalizations()
				.stream()
				.map(PersonalizationDto::new)
				.collect(Collectors.toList());
	}
}