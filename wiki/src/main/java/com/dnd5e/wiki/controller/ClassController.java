package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.classes.Archetype;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.repository.ClassRepository;

@Controller
@RequestMapping("/hero/classes")
public class ClassController {

	private ClassRepository repository;

	@Autowired
	public void setClassRepository(ClassRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public String getClasses(Model model) {
		model.addAttribute("classes", repository.findAll());
		return "classes";
	}

	@Autowired
	private HttpSession session;
	
	@GetMapping("/class/{id}")
	public String getClass(Model model, @PathVariable Integer id) {
		HeroClass heroClass = repository.findById(id).get();
		Setting settings = (Setting) session.getAttribute(SettingRestController.HOME_RULE);
		if (settings == null || !settings.isHomeRule()) {
			List<Archetype> archetypes = heroClass.getArchetypes().stream().filter(a -> a.getBook().getType() == TypeBook.OFFICAL).collect(Collectors.toList());
			heroClass.setArchetypes(archetypes);
		}
		model.addAttribute("heroClass", heroClass);
		return "classView";
	}
}
