package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.repository.BackgroundRepository;

@Controller
@RequestMapping("/hero/backgrounds")
public class BackgroundController {
	@Autowired
	private BackgroundRepository repo;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping
	public String getBackgrounds(Model model) {
		List<Background> backgrounds = repo.findAll(Sort.by(Sort.Direction.ASC, "name"));
		Setting settings = (Setting) session.getAttribute(SettingRestController.HOME_RULE);
		if (settings != null && !settings.isHomeRule()) {
			backgrounds = backgrounds.stream()
					.filter(r -> r.getBook().getType() == TypeBook.OFFICAL)
					.collect(Collectors.toList());
		}
		model.addAttribute("backgrounds", backgrounds);
		return "hero/backgrounds";
	}
}