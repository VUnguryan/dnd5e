package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/magic/books")
public class MagicBookController {
	@GetMapping
	public String getMagicBooksForm(Model model)
	{
		return "/workshop/magicBooks";
	}
}