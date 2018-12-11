package com.dnd5e.wiki.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.paces.Place;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.PlaceRepository;

@Controller
public class HomeController {
	private PlaceRepository repo;

	@Autowired
	private void setRepository(PlaceRepository repo)
	{
		this.repo = repo;
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String getHome(Model model) {
		List<Place> places = repo.findByParentIsNull();
		model.addAttribute("places", places);
		return "home";
	}

	@GetMapping("/place/{id}")
	@Transactional
	public String getPlace(Model model, @PathVariable Integer id) {
		Place place = repo.findById(id).get();
		model.addAttribute("place", repo.findById(id).get());
		model.addAttribute("children", place.getChildren());
		return "place";
	}
	
	@GetMapping("/conditions/")
	public String getConditions()
	{
		return "condition";
	}
}