package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.repository.BackgroundRepository;

@Controller
@RequestMapping("/hero/backgrounds")
public class BackgroundController {
	@Autowired
	private BackgroundRepository repo;
	
	@GetMapping
	public String getBackgrounds(Model model) {
		model.addAttribute("backgrounds", repo.findAll());
		return "hero/backgrounds";
	}
}
