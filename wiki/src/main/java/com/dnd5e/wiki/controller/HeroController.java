package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.hero.Condition;
import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.classes.Archetype;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.repository.ArchetypeRepository;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.ConditionRepository;
import com.dnd5e.wiki.repository.HeroClassFeatRepository;

@Controller
public class HeroController {
	private ConditionRepository conditionRepositoryl;
	private ClassRepository classRepo;
	private ArchetypeRepository archetyprRepo;
	private HeroClassFeatRepository featRepo;

	@Autowired
	public void setConditionRepositoryl(ConditionRepository conditionRepositoryl) {
		this.conditionRepositoryl = conditionRepositoryl;
	}

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

	@GetMapping("/conditions")
	public String getCondinons(Model model) {
		model.addAttribute("conditions", conditionRepositoryl.findAll());
		return "/hero/conditions";
	}

	@RequestMapping(value = "conditions", params = "search" )
	public String searchConditions(Model model, String search) {
		List<Condition> conditions = conditionRepositoryl.findAll(byName(search));
		model.addAttribute("conditions", conditions);
		return "/hero/conditions";
	}

	@GetMapping("/archetype/add")
	public String getArchitypeForm(Model model) {

		model.addAttribute("classes", classRepo.findAll());
		model.addAttribute("archetype", new Archetype());
		return "/hero/addClassType";
	}

	@PostMapping("/archetype/add")
	public String addArchitype(Archetype archetype) {
		archetyprRepo.save(archetype);
		return "redirect:/archetype/add";
	}

	@GetMapping("/archetype/feat/add")
	public String getArchitypeFeat(Model model) {
		model.addAttribute("classes", classRepo.findAll());
		return "/hero/addClassTypeFeat";
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

		return "/hero/addClassTypeFeat";
	}

	@PostMapping("/archetype/feat/add")
	public String addArchitypeFeat(ArchetypeTrait feat, Integer classTypeId) {
		featRepo.save(feat);
		return "redirect:/archetype/feat/add?classTypeId=" + classTypeId + "&archiTypeId="
				+ feat.getArchetype().getId();
	}

	private Specification<Condition> byName(String search) {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("englishName"), "%" + search + "%"));
	}
}