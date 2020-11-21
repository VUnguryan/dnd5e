package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hero/")
public class HeroController {
	@GetMapping("/conditions")
	public String getCondinons(Model model) {
		return "datatable/conditions";
	}

	@GetMapping("/options")
	public String getTableOptions() {
		return "datatable/options";
	}

	@GetMapping("/traits")
	public String getTableTraits() {
		return "datatable/traits";
	}
}