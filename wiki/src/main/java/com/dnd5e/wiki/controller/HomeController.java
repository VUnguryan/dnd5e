package com.dnd5e.wiki.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.places.Place;
import com.dnd5e.wiki.repository.PlaceRepository;
import com.dnd5e.wiki.repository.ReferenceRepository;

@Controller
public class HomeController {
	@Autowired
	private PlaceRepository placeRepo;
	@Autowired
	private ReferenceRepository referenceRepo;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String getHome(Model model, Device device) {
		model.addAttribute("device", device);
		return "home";
	}

	@GetMapping("/place/{id}")
	@Transactional
	public String getPlace(Model model, @PathVariable Integer id) {
		Place place = placeRepo.findById(id).get();
		model.addAttribute("place", placeRepo.findById(id).get());
		model.addAttribute("children", place.getChildren());
		return "place";
	}

	@GetMapping("/references")
	public String getReferences(Model model, Device device) {
		model.addAttribute("device", device);

		model.addAttribute("references", referenceRepo.findAll());
		return "references";
	}

	@GetMapping("/403")
	public String getAccessError() {
		return "403";
	}
}