package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	public HomeController() {
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String getHome() {
		return "home";
	}
}