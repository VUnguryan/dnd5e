package com.dnd5e.wiki.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.places.Place;
import com.dnd5e.wiki.repository.PlaceRepository;

@Controller
@RequestMapping("/places")
public class PlaceController {
	
	private PlaceRepository repo;
	
	@Autowired
	private void setRepository(PlaceRepository repo)
	{
		this.repo = repo;
	}
	
	@GetMapping()
	public String getForm(Model model)
	{
		model.addAttribute("places", repo.findAll(Sort.by("name")));
		model.addAttribute("place", new Place());
		return "addPlace";
	}
	
	@GetMapping("/place/{id}")
	public String getPlace(Model model, Integer id)
	{
		Optional<Place> place = repo.findById(id);
		model.addAttribute("place", place.get());
		return "addPlace";
	}

	@PostMapping("/add")
	public String addPlace(Place place)
	{
		repo.save(place);
		return "redirect:/places/";
	}
}