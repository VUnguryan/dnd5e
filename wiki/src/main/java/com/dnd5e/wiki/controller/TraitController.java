package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hero/traits")
public class TraitController {
	
	@GetMapping
	public String getTableTrait()
	{
		return "datatable/traits";
	}
}
