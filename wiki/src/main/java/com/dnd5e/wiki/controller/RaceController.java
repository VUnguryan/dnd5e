package com.dnd5e.wiki.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.race.Feature;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.repository.RaceRepository;
import com.dnd5e.wiki.util.SourceUtil;

@Controller
@RequestMapping("/hero/races")
public class RaceController {
	private  RaceRepository repo;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public void setRepo(RaceRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping
	public String getRaces(Model model) {
		List<Race> races = repo.findByParentIsNull(Sort.by(Sort.Direction.ASC, "name"));
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		races = races.stream()
					.filter(r -> sources.contains(r.getBook().getType()))
					.collect(Collectors.toList());
		model.addAttribute("races", races);
		return "hero/races";
	}

	@GetMapping("/race/{id}")
	public String getRace(Model model, @PathVariable Integer id) {
		Race race = repo.findById(id).orElseThrow(NoSuchElementException::new);
		model.addAttribute("race", race);
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		List<Feature> features = new ArrayList<>();
		if (race.getParent() != null) {
			model.addAttribute("subRaces", race.getParent().getSubRaces().stream()
					.filter(r -> sources.contains(r.getBook().getType()))
					.collect(Collectors.toList()));
			features.addAll(race.getFeatures());
			features.addAll(race.getParent().getFeatures());
		}
		else {
			model.addAttribute("subRaces", race.getSubRaces().stream()
					.filter(r -> sources.contains(r.getBook().getType()))
					.collect(Collectors.toList()));
			features.addAll(race.getFeatures());
		}
		model.addAttribute("features", features);
		return "hero/raceView";
	}
}