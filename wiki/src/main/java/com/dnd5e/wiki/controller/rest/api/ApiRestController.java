package com.dnd5e.wiki.controller.rest.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.model.api.BackgroundApi;
import com.dnd5e.wiki.controller.rest.model.api.ClassApiDto;
import com.dnd5e.wiki.controller.rest.model.api.ClassApiInfoDto;
import com.dnd5e.wiki.controller.rest.model.api.GodApi;
import com.dnd5e.wiki.controller.rest.model.api.GodInfoApi;
import com.dnd5e.wiki.controller.rest.model.api.PersonalizationApi;
import com.dnd5e.wiki.controller.rest.model.api.SpellApiDto;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.repository.BackgroundRepository;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.GodRepository;
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
	
	@Autowired
	private GodRepository godRepository;

	@GetMapping("/spells")
	public List<SpellApiDto> getSpells(){
		return spellRepository.findAll().stream().map(SpellApiDto::new).collect(Collectors.toList());
	}

	@GetMapping("/classes")
	public List<ClassApiDto> getClasses(){
		return classRepository.findAll().stream().map(ClassApiDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/classes/{id}")
	public ResponseEntity<ClassApiInfoDto> getClass(@PathVariable Integer id){
		return ResponseEntity.ok(new ClassApiInfoDto(classRepository.findById(id).orElseGet(() -> new HeroClass())));
	}
	
	@GetMapping("/backgrounds")
	public List<BackgroundApi> getBackgrounds(){
		return backgroundRepository.findAll().stream().map(BackgroundApi::new).collect(Collectors.toList());
	}
	
	@GetMapping("/backgrounds/{id}/personalization")
	public List<PersonalizationApi> getBackground(@PathVariable Integer id){
		return backgroundRepository.findById(id).orElseGet(() -> new Background()).getPersonalizations()
				.stream()
				.map(PersonalizationApi::new)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/gods")
	public ResponseEntity<List<GodApi>> getGods(){
		return ResponseEntity.ok(godRepository.findAll().stream().map(GodApi::new).collect(Collectors.toList()));
	}
	
	@GetMapping("/gods/{id}")
	public ResponseEntity<GodInfoApi> getGod(@PathVariable Integer id){
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(godRepository.findById(id).map(GodInfoApi::new).orElseGet(GodInfoApi::new));
	}
}