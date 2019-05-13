package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.repository.TraitRepository;

@Controller
@RequestMapping("/hero/traits")
public class TraitController {
	@Autowired
	private TraitRepository repo;

	@GetMapping
	public String getTraits(Model model) {
		model.addAttribute("traits", repo.findAll(Sort.by(Sort.Direction.ASC, "name")));
		return "hero/traits";
	}
}
