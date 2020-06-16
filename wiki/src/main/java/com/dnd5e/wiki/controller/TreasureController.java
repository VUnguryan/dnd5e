package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stock")
public class TreasureController {
	@GetMapping("/artifacts")
	public String getMagicThings() {
		return "datatable/magicThings";
	}

	@GetMapping("/treasures")
	public String getTreasures() {
		return "datatable/treasures";
	}
}