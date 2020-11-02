package com.dnd5e.wiki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.travel.Transport;
import com.dnd5e.wiki.repository.TransportRepository;

@Controller
@RequestMapping("/travel/transports")
public class TransportController {
	@Autowired
	TransportRepository repo;

	@GetMapping
	public String getTransports(Model model) {
		List<Transport> transports = repo.findAll();
		model.addAttribute("transports", transports);
		return "transports";
	}
}
