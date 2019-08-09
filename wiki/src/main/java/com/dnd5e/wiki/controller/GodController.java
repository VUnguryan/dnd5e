package com.dnd5e.wiki.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.GodSex;
import com.dnd5e.wiki.repository.GodRepository;

@Controller
@RequestMapping("/gods")
@Scope("session")
public class GodController {
	private Set<GodSex> sex;
	private Set<Domain> domains;

	@Autowired
	private GodRepository repository;
	
	@GetMapping
	public String getGods(Model model, @PageableDefault(size = 12, sort = "name") Pageable page)
	{
		model.addAttribute("gods", repository.findAll(page));
		return "gods";
	}
}