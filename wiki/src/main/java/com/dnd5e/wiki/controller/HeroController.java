package com.dnd5e.wiki.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.classes.Archetype;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.repository.ArchetypeRepository;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.HeroClassFeatRepository;

@Controller
public class HeroController {
	private ClassRepository classRepo;
	private ArchetypeRepository archetyprRepo;
	private HeroClassFeatRepository featRepo;

	@Autowired
	public void setClassRepository(ClassRepository repository) {
		this.classRepo = repository;
	}

	@Autowired
	public void setArchetyprRepo(ArchetypeRepository archetyprRepo) {
		this.archetyprRepo = archetyprRepo;
	}

	@Autowired
	public void setFeatRepo(HeroClassFeatRepository featRepo) {
		this.featRepo = featRepo;
	}

	@GetMapping("/hero/conditions")
	public String getCondinons(Model model) {
		return "datatable/conditions";
	}

	@GetMapping("/archetype/add")
	public String getArchitypeForm(Model model) {

		model.addAttribute("classes", classRepo.findAll());
		model.addAttribute("archetype", new Archetype());
		return "hero/addClassType";
	}

	@PostMapping("/archetype/add")
	public String addArchitype(Archetype archetype) {
		archetyprRepo.save(archetype);
		return "redirect:/archetype/add";
	}

	@GetMapping("/archetype/feat/add")
	public String getArchitypeFeat(Model model) {
		model.addAttribute("classes", classRepo.findAll());
		return "hero/addClassTypeFeat";
	}

	@GetMapping(value = "/archetype/feat/add", params = { "classTypeId", "archiTypeId" })
	public String getArchitypeFeatForm(Model model, Integer classTypeId, Integer archiTypeId) {
		model.addAttribute("classes", classRepo.findAll());
		Optional<HeroClass> heroClass = classRepo.findById(classTypeId);
		if (heroClass.isPresent()) {
			model.addAttribute("archetypes", heroClass.get().getArchetypes());
		}
		model.addAttribute("classTypeId", classTypeId);
		model.addAttribute("archiTypeId", archiTypeId);
		model.addAttribute("feat", new ArchetypeTrait());

		return "hero/addClassTypeFeat";
	}

	@PostMapping("/archetype/feat/add")
	public String addArchitypeFeat(ArchetypeTrait feat, Integer classTypeId) {
		featRepo.save(feat);
		return "redirect:/archetype/feat/add?classTypeId=" + classTypeId + "&archiTypeId="
				+ feat.getArchetype().getId();
	}
}