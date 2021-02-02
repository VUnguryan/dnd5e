package com.dnd5e.wiki.controller.rest.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.model.api.BackgroundApi;
import com.dnd5e.wiki.controller.rest.model.api.ClassApi;
import com.dnd5e.wiki.controller.rest.model.api.ClassInfoApi;
import com.dnd5e.wiki.controller.rest.model.api.PersonalizationApi;
import com.dnd5e.wiki.controller.rest.model.api.SpellApi;
import com.dnd5e.wiki.controller.rest.model.api.gods.GodApi;
import com.dnd5e.wiki.controller.rest.model.api.gods.GodInfoApi;
import com.dnd5e.wiki.model.Archive;
import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;
import com.dnd5e.wiki.model.gods.Pantheon;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.repository.ArchiveRepository;
import com.dnd5e.wiki.repository.BackgroundRepository;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.GodRepository;
import com.dnd5e.wiki.repository.PantheonRepository;
import com.dnd5e.wiki.repository.SpellRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController()
@RequestMapping("/api")
public class ApiRestController {
	@Autowired
	private ArchiveRepository archiveRepository;
	
	@Autowired
	private BackgroundRepository backgroundRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private SpellRepository spellRepository;
	
	@Autowired
	private GodRepository godRepository;
	
	@Autowired
	private PantheonRepository pantheonRepository;
	
	@GetMapping("/spells")
	public List<SpellApi> getSpells(){
		return spellRepository.findAll().stream().map(SpellApi::new).collect(Collectors.toList());
	}

	@GetMapping("/classes")
	public List<ClassApi> getClasses(){
		return classRepository.findAll().stream().map(ClassApi::new).collect(Collectors.toList());
	}
	
	@GetMapping("/classes/{id}")
	public ResponseEntity<ClassInfoApi> getClass(@PathVariable Integer id){
		return ResponseEntity.ok(new ClassInfoApi(classRepository.findById(id).orElseGet(() -> new HeroClass())));
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
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<GodInfoApi> getGod(@PathVariable Integer id){
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(godRepository.findById(id).map(GodInfoApi::new).orElseGet(GodInfoApi::new));
	}
	
	@Transactional
	@PostMapping("/gods/")
	@ResponseStatus(HttpStatus.CREATED)
	public GodInfoApi addGod(GodInfoApi godInfoApi){
		God god = GodInfoApi.build(godInfoApi);
		godRepository.save(god);
		return godInfoApi;
	}
	
	@Transactional
	@PutMapping("/gods/")
	@ResponseStatus(HttpStatus.CREATED)
	public GodInfoApi updateGod(GodInfoApi godInfoApi) throws JsonProcessingException{
		God god = godRepository.findById(godInfoApi.getId()).orElseThrow(NullPointerException::new);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(godInfoApi);

		Archive archive = new Archive();
		archive.setType("god");
		archive.setText(json);
		archive.setVersion(god.getVersion());
		archiveRepository.save(archive);

		GodInfoApi.update(god, godInfoApi);
		god.setVersion(god.getVersion() + 1);
		return godInfoApi;
	}
	
	@GetMapping("/gods/pantheons")
	public ResponseEntity<List<Pantheon>> getPantheon(){
		return ResponseEntity.ok(pantheonRepository.findAll());
	}
	
	@GetMapping("/gods/domains")
	public ResponseEntity<List<Domain>> getDomains(){
		return ResponseEntity.ok(Arrays.asList(Domain.values()));
	}
}