package com.dnd5e.wiki.controller;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.hero.race.RaceName;
import com.dnd5e.wiki.model.hero.race.Sex;
import com.dnd5e.wiki.repository.RaceNameRepopsitory;
import com.dnd5e.wiki.repository.RaceRepository;

@Controller
@RequestMapping("/admin/race/name")
public class RaceNameController {
	@Autowired
	private RaceNameRepopsitory nameRepo;
	
	private RaceRepository repo;

	@Autowired
	public void setRepo(RaceRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public String getForm(Model model) {
		model.addAttribute("races", repo.findAll(Sort.by("name")));
		model.addAttribute("sexs", Sex.values());
		return "/admin/addRaceName";
	}

	@PostMapping()
	public String parseNames(String raceId, String text, String sex) {
		
		System.out.println(raceId + " " + sex + " " + text);
		if (raceId != null) {
			Race race = repo.findById(Integer.valueOf(raceId)).orElseGet(Race::new);
			Set<RaceName> names = nameRepo.findByRace(race);
			Arrays.stream(text.split(","))
				.map(String::trim)
				.map(n -> new RaceName(null, n, Sex.valueOf(sex), race))
				.filter(rn -> !names.contains(rn))
				.forEach(r -> nameRepo.save(r));
				
		}
		return "redirect:/admin/race/name";
	}
}