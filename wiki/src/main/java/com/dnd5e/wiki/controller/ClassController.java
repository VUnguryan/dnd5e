package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.hero.HeroClass;
import com.dnd5e.wiki.repository.ClassRepository;

@Controller
@RequestMapping("/classes")
public class ClassController {
	
	private ClassRepository repository;
	
	@Autowired
	public void setClassRepository(ClassRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getClasses(Model model){
		model.addAttribute("classes", repository.findAll());
		return "classes";
	}
	@RequestMapping(value = { "/class/{id}" }, method = RequestMethod.GET)
	public String getClass(Model model, @PathVariable Integer id) {
		HeroClass heroClass = repository.findById(id).get();
		model.addAttribute("heroClass", heroClass);
		return "classView";
	}
}
