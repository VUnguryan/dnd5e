package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.feat.Feat;
import com.dnd5e.wiki.repository.FeatRepository;

@Controller
@RequestMapping("/feats")
final class FeatController {
	private FeatRepository featRepository;

	@Autowired
	public void setFeatRepository(FeatRepository featRepository) {
		this.featRepository = featRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getFeats(Model model) {
		model.addAttribute("order", Integer.valueOf(1));
		model.addAttribute("feats", featRepository.findAll());
		return "feats";
	}

	@RequestMapping(value = { "/feat/{id}" }, method = RequestMethod.GET)
	public String getFeat(Model model, @PathVariable Integer id) {
		Feat feat = featRepository.findById(id).get();
		model.addAttribute("feat", feat);
		return "featView";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String getAddForm(Model model) {
		model.addAttribute("feat", new Feat());
		return "addFeat";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String getArtifact(@ModelAttribute Feat feat) {

		if (!featRepository.findByNameContaining(feat.getName()).isEmpty()) {
			return "redirect:/feats/add";
		}

		featRepository.save(feat);
		return "redirect:/feats/add";
	}
}
