package com.dnd5e.wiki.controller.tavern;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

import com.dnd5e.wiki.model.creature.Ability;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.tavern.Hero;
import com.dnd5e.wiki.model.user.User;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.HeroRepository;
import com.dnd5e.wiki.repository.RaceRepository;
import com.dnd5e.wiki.repository.UserRepository;

@Controller
@RequestMapping("tavern")
public class TavernController {
	private ClassRepository classRepo;
	private RaceRepository raceRepo;
	private HeroRepository heroRepo;
	private UserRepository userRepo;

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

	@Autowired
	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@GetMapping
	public String getHeroes(Model model, Authentication authentication,
			@PageableDefault(size = 12, sort = "name") Pageable page) {
		if (authentication != null) {
			User user = userRepo.findByName(authentication.getName());
			model.addAttribute("heroes", heroRepo.findByUserId(user.getId(), page));
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
		return "/tavern/formHero";
	}

	@PostMapping("/add")
	public String createHero(Hero hero, Authentication authentication) {
		hero.setUser(userRepo.findByName(authentication.getName()));
		authentication.getName();
		hero.setHp(hero.getHeroClass().getDiceHp());
		hero = heroRepo.save(hero);
		return "redirect:/tavern/hero/" + hero.getId();
	}

	@GetMapping("/hero/{id}")
	public String getBuilderForm(Model model, @PathVariable Integer id) {
		Hero hero = heroRepo.getOne(id);
		Map<Ability, List<SkillType>> skills = Arrays.stream(SkillType.values())
				.sorted((s1,s2)->s2.getAbility().ordinal() - s1.getAbility().ordinal())
				.collect(Collectors.groupingBy(SkillType::getAbility));
		model.addAttribute("hero", hero);
		model.addAttribute("skillTypes", skills);
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
