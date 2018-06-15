package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.artifact.Artifact;
import com.dnd5e.wiki.model.artifact.ArtifactType;
import com.dnd5e.wiki.model.artifact.Rarity;
import com.dnd5e.wiki.repository.ArtifactRepository;

@Controller
@RequestMapping("/artifacts")
public class ArtifactController {
	@Autowired
	private ArtifactRepository repository;

	public ArtifactController() {
	}

	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String getArtifactes(Model model) {
		model.addAttribute("artifacts", repository.findAll());
		model.addAttribute("rarityTypes", Rarity.values());
		model.addAttribute("artifactTypes", ArtifactType.values());
		model.addAttribute("order", Integer.valueOf(1));
		return "artifacts";
	}

	@RequestMapping(value = { "/add" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String getAddForm(Model model) {
		model.addAttribute("artifact", new Artifact());
		return "addArtifact";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String getArtifact(@org.springframework.web.bind.annotation.ModelAttribute Artifact artifact) {
		StringReader reader = new StringReader(artifact.getDescription());
		LineNumberReader lr = new LineNumberReader(reader);
		String line = null;
		StringBuilder builder = new StringBuilder();
		try {
			while ((line = lr.readLine()) != null) {
				builder.append(removeHyphenation(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		artifact.setDescription(builder.toString());
		repository.save(artifact);
		return "redirect:/artifacts/add";
	}

	@RequestMapping(value = { "/artifact/{id}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String getArtifact(Model model, @PathVariable Integer id) {
		Artifact artifac = (Artifact) repository.findById(id).get();
		model.addAttribute("artifact", artifac);
		return "artifactView";
	}

	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.GET }, params = { "search" })
	public String searchArtifacts(Model model, String search) {
		model.addAttribute("artifacts", repository.findByNameContaining(search));
		model.addAttribute("rarityTypes", Rarity.values());
		model.addAttribute("artifactTypes", ArtifactType.values());
		return "artifacts";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "type" })
	public String filterArtifact(Model model, String type, String rarity) {
		List<Artifact> artifacts = Collections.emptyList();
		if (("ALL".equals(type)) && ("ALL".equals(rarity))) {
			artifacts = repository.findAll();
		} else if ((!"ALL".equals(type)) && ("ALL".equals(rarity))) {
			ArtifactType typeSelected = ArtifactType.valueOf(type);
			artifacts = repository.findByType(typeSelected);
			model.addAttribute("typeSelected", typeSelected);
		} else if (("ALL".equals(type)) && (!"ALL".equals(rarity))) {
			Rarity raritySelected = Rarity.valueOf(rarity);
			artifacts = repository.findByRarity(raritySelected);
			model.addAttribute("raritySelected", raritySelected);
		} else {
			ArtifactType typeSelected = ArtifactType.valueOf(type);
			Rarity raritySelected = Rarity.valueOf(rarity);
			artifacts = repository.findByTypeAndRarity(typeSelected, raritySelected);
			model.addAttribute("typeSelected", typeSelected);
			model.addAttribute("raritySelected", raritySelected);
		}
		model.addAttribute("artifacts", artifacts);
		model.addAttribute("rarityTypes", Rarity.values());
		model.addAttribute("artifactTypes", ArtifactType.values());
		return "artifacts";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "order" })
	public String sortSpells(Model model, Integer order, String dir) {
		Sort.Direction direction = Sort.Direction.ASC;
		Sort sort = null;
		switch (order.intValue()) {
		case 0:
			sort = new Sort(direction, "name");
			break;
		case 1:
			sort = new Sort(direction, "rarity");
			break;
		case 2:
			sort = new Sort(direction, "type");
			break;
		default:
			sort = Sort.unsorted();
		}
		model.addAttribute("rarityTypes", Rarity.values());
		model.addAttribute("artifactTypes", ArtifactType.values());
		model.addAttribute("artifacts", repository.findAll(sort));
		return "artifacts";
	}

	private String removeHyphenation(String string) {
		return string + " ";
	}
}