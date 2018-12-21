package com.dnd5e.wiki.controller.tavern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.tavern.Hero;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.HeroRepository;
import com.dnd5e.wiki.repository.RaceRepository;

@Controller
@RequestMapping("tavern")
public class TavernController {
	private ClassRepository classRepo;
	private RaceRepository raceRepo;
	private HeroRepository heroRepo;

	@Autowired
	public void setHeroRepo(HeroRepository heroRepo) {
		this.heroRepo = heroRepo;
	}
	@Autowired
	public void setRepo(RaceRepository repository) {
		this.raceRepo = repository;
	}

	@Autowired
	public void setClassRepository(ClassRepository repository) {
		this.classRepo = repository;
	}

	@GetMapping
	public String getHeroes(Model model) {
		return "/tavern/heroes";
	}
	@GetMapping("/add")
	public String getFormHero(Model model) {
		model.addAttribute("classes", classRepo.findAll());
		model.addAttribute("races", raceRepo.findAll());
		model.addAttribute("hero", new Hero());
		return "formHero";
	}
	
	@PostMapping
	public String createHero(Hero hero) {
		heroRepo.save(hero);
		return "redirect:/tavern";
	}
}
