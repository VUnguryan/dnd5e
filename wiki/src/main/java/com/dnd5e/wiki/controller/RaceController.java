package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.repository.RaceRepository;

@Controller
@RequestMapping("/hero/races")
public class RaceController {
	private  RaceRepository repo;
	
	@Autowired
	public void setRepo(RaceRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping
	public String getRaces(Model model) {
		model.addAttribute("races", repo.findAll());
		return "/hero/races";
	}
	
	@GetMapping("/race/{id}")
	public String getRace(Model model, @PathVariable Integer id) {
		model.addAttribute("race", repo.findById(id).get());
		return "/hero/raceView";
	}
}