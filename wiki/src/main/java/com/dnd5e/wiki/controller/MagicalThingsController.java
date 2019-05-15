package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.artifact.Artifact;
import com.dnd5e.wiki.model.artifact.ArtifactType;
import com.dnd5e.wiki.model.artifact.Rarity;
import com.dnd5e.wiki.repository.ArtifactRepository;

@Controller
@RequestMapping("/stock/artifacts")
@Scope("session")
public class MagicalThingsController {
	private Optional<String> search = Optional.empty(); 
	private Optional<Rarity> rarityFilter = Optional.empty();
	private Optional<ArtifactType> typeFilter = Optional.empty();

	private ArtifactRepository repository;

	@Autowired
	public void setRepository(ArtifactRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public String getArtifactes(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<Artifact> specification = null;
		if (search.isPresent()) {
			specification = byName(search.get());
		} 
		if(typeFilter.isPresent() ) {
			if (specification == null) {
				specification = byType();
			} else {
				specification = Specification.where(specification).and(byType());
			}
		}
		if (rarityFilter.isPresent()) {
			if (specification == null) {
				specification = byRarity();
			} else {
				specification = Specification.where(specification).and(byRarity());
			}
		}
		if (specification == null) {
			model.addAttribute("artifacts", repository.findAll(page));
		} else {
			model.addAttribute("artifacts", repository.findAll(specification, page));
		}
		
		model.addAttribute("typeSelected", typeFilter);
		model.addAttribute("raritySelected", rarityFilter);
		model.addAttribute("rarityTypes", Rarity.values());
		model.addAttribute("artifactTypes", ArtifactType.values());
		model.addAttribute("order", Integer.valueOf(1));
		model.addAttribute("searchText", search);
		return "equipment/magicalThings";
	}

	@GetMapping(params = "search")
	public String searchArtifacts(Model model, String search) {
		if (search.isEmpty()) {
			this.search = Optional.empty();
		}
		else
		{
			this.search = Optional.of(search);
		}
		return "redirect:/stock/artifacts";
	}

	@GetMapping(params = { "sort", "type", "rarity" })
	public String filterArtifact(Model model, String sort, String type, String rarity) {
		if ("ALL".equals(type)) {
			this.typeFilter = Optional.empty();
		}
		else
		{
			ArtifactType typeSelected = ArtifactType.valueOf(type);
			this.typeFilter = Optional.of(typeSelected); 
		}
		if ("ALL".equals(rarity))
		{
			this.rarityFilter = Optional.empty();			
		}
		else
		{
			Rarity raritySelected = Rarity.valueOf(rarity);
			this.rarityFilter = Optional.of(raritySelected);
		}
		return "redirect:/stock/artifacts?sort=" + sort;
	}

	@GetMapping("/add")
	public String getAddForm(Model model) {
		model.addAttribute("rarityTypes", Rarity.values());
		model.addAttribute("artifactTypes", ArtifactType.values());
		model.addAttribute("artifact", new Artifact());
		return "equipment/addArtifact";
	}

	@PostMapping("/add")
	public String getArtifact(@ModelAttribute Artifact artifact) {

		if (!repository.findByNameContaining(PageRequest.of(1, 1), artifact.getName()).getContent().isEmpty()) {
			return "redirect:/stock/artifacts/add";
		}
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
		return "redirect:/stock/artifacts/add";
	}

	private String removeHyphenation(String string) {
		return string + " ";
	}

	private Specification<Artifact> byName(String search) {
		return (root, query, cb) -> cb.and(cb.like(root.get("name"), "%" + search + "%"));
	}

	private Specification<Artifact> byRarity() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("rarity"), rarityFilter.get()));
	}
	
	private Specification<Artifact> byType() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("type"), typeFilter.get()));
	}
}