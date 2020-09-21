package com.dnd5e.wiki.controller.tavern;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.tavern.Hero;
import com.dnd5e.wiki.model.user.User;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.EquipmentRepository;
import com.dnd5e.wiki.repository.HeroRepository;
import com.dnd5e.wiki.repository.RaceRepository;
import com.dnd5e.wiki.repository.UserRepository;

@Controller
@RequestMapping("/tavern")
public class PcController {
	@Autowired
	private ClassRepository classRepo;
	@Autowired
	private RaceRepository raceRepo;
	@Autowired
	private HeroRepository heroRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private EquipmentRepository equipmentRepo;

	@GetMapping
	public String getHeroes(Model model, Authentication authentication,
			@PageableDefault(size = 12, sort = "name") Pageable page) {
		if (authentication != null) {
			Optional<User> user = userRepo.findByName(authentication.getName());
			model.addAttribute("heroes", heroRepo.findByUserId(user.orElseGet(User::new).getId(), page));
		}
		else
		{
			model.addAttribute("heroes", Collections.emptyList());
		}
		return "/tavern/heroes";
	}

	@GetMapping("/add")
	public String getFormHero(Model model) {
		model.addAttribute("classes", classRepo.findAll());
		model.addAttribute("races", raceRepo.findAll());
		model.addAttribute("hero", new Hero());
		return "/tavern/createHero";
	}

	@PostMapping("/add")
	public String createHero(Hero hero, Authentication authentication) {
		Optional<User> user = userRepo.findByName(authentication.getName());
		hero.setUser(user.get());
		authentication.getName();
		hero.setHp(hero.getHeroClass().getDiceHp());
		hero = heroRepo.save(hero);
		return "redirect:/tavern/hero/" + hero.getId();
	}

	@GetMapping("/hero/{id}")
	public String getBuilderForm(Model model, @PathVariable Integer id) {
		Hero hero = heroRepo.getOne(id);
		model.addAttribute("hero", hero);
		model.addAttribute("skillTypes", SkillType.getSkillsToAbbility());
		model.addAttribute("equipments", equipmentRepo.findAll());
		if (hero.getLevel()>=hero.getHeroClass().getEnabledArhitypeLevel()) {
			model.addAttribute("architypes", hero.getHeroClass().getArchetypes());
		}
		return "/tavern/formBuilderInfo";
	}

	@PostMapping("/hero/{id}")
	public String saveGero(Model model, @PathVariable Integer id, Hero hero) {
		hero = heroRepo.saveAndFlush(hero);
		return "redirect:/tavern/hero/" + hero.getId();
	}
}