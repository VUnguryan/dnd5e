package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hero/backgrounds")
public class BackgroundController {
	@GetMapping
	public String getBackgrounds(Model model) {
		return "datatable/backgrounds";
	}
}