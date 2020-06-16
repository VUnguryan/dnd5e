package com.dnd5e.wiki.controller;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.hero.Personalization;
import com.dnd5e.wiki.model.hero.PersonalizationType;
import com.dnd5e.wiki.repository.BackgroundRepository;

@Controller
@RequestMapping("/hero/backgrounds")
public class BackgroundController {
	private static final Random rnd = new Random();
	
	@Autowired
	private BackgroundRepository repo;
	
	@GetMapping
	public String getBackgrounds() {
		return "datatable/backgrounds";
	}
	
	@GetMapping("/personalizare/{id}")
	public String getPersonalizare(Model model, @PathVariable Integer id) {
		Background background = repo.findById(id).orElseGet(Background::new);
		model.addAttribute("name", background.getName());
		model.addAttribute("description", background.getPersonalization());
		List<Personalization> personalizations = new ArrayList<>();
		Map<PersonalizationType, List<Personalization>> map = background.getPersonalizations().stream()
				.collect(Collectors.groupingBy(Personalization::getType, () -> new EnumMap<>(PersonalizationType.class), Collectors.toList()));
		for (PersonalizationType type:map.keySet()) {
			List<Personalization> list = map.get(type);
			personalizations.add(list.get(rnd.nextInt(list.size())));
		}
		model.addAttribute("personalizations", personalizations);
		return "hero/personalizare";
	}
}