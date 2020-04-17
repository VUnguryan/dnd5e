package com.dnd5e.wiki.controller;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.repository.BookRepository;
import com.dnd5e.wiki.repository.TraitRepository;

@Controller
@Scope("session")
@RequestMapping("/hero/traits")
public class TraitController {
	@Autowired
	private TraitRepository repo;
	@Autowired
	private BookRepository bookRepo;

	private Set<AbilityType> selectedAbilities = EnumSet.noneOf(AbilityType.class);
	private Set<SkillType> selectedSkills = EnumSet.noneOf(SkillType.class);

	private Set<String> sources;
	private int sourceSize;

	private String search = "";

	@PostConstruct
	public void initClassses() {
		this.sources = bookRepo.finadAllByLeftJoinHeroTrait().stream().filter(Objects::nonNull).map(Book::getSource)
				.collect(Collectors.toSet());
		this.sourceSize = sources.size();
	}

	@GetMapping
	public String getTraits(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<Trait> specification = null;
		if (!search.isEmpty()) {
			specification = byName();
		}
		if (!sources.isEmpty()) {
			if (specification == null) {
				specification = bySource();
			} else {
				specification = Specification.where(specification).and(bySource());
			}
		}
		if (!selectedAbilities.isEmpty()) {
			if (specification == null) {
				specification = byAbility();
			} else {
				specification = Specification.where(specification).and(byAbility());
			}
		}
		if (!selectedSkills.isEmpty()) {
			if (specification == null) {
				specification = byAbility();
			} else {
				specification = Specification.where(specification).and(bySkill());
			}
		}
		if (specification == null) {
			model.addAttribute("traits", repo.findAll(page));
		} else {
			model.addAttribute("traits", repo.findAll(specification, page));
		}

		model.addAttribute("abilities", AbilityType.values());
		model.addAttribute("skills", SkillType.values());
		model.addAttribute("selectedAbilities", selectedAbilities);
		model.addAttribute("selectedSkills", selectedSkills);

		model.addAttribute("searchText", search);
		model.addAttribute("books", bookRepo.finadAllByLeftJoinHeroTrait());
		model.addAttribute("selectedBooks", sources);
		model.addAttribute("searchText", search);
		model.addAttribute("filtered", sources.size() != sourceSize || !selectedAbilities.isEmpty() || !selectedSkills.isEmpty());
		return "hero/traits";
	}

	@GetMapping(params = { "search" })
	public String searchSpells(Model model, String search) {
		this.search = search.trim();
		return "redirect:/hero/traits?sort=name,asc";
	}

	@GetMapping(params = { "abilityType" })
	public String filterByAbility(Model model, String[] abilityType) {
		selectedAbilities = Arrays.stream(abilityType).map(AbilityType::valueOf).collect(Collectors.toSet());
		return "redirect:/hero/traits?sort=name,asc";
	}
	
	@GetMapping(params = { "skillType" })
	public String filterBySkill(Model model, String[] skillType) {
		selectedSkills= Arrays.stream(skillType).map(SkillType::valueOf).collect(Collectors.toSet());
		return "redirect:/hero/traits?sort=name,asc";
	}
	
	@GetMapping(params = { "clear" })
	public String cleaarFilters(Model model, String search) {
		this.search = "";
		selectedAbilities.clear();
		selectedSkills.clear();
		this.sources = bookRepo.finadAllByLeftJoinHeroTrait().stream().filter(Objects::nonNull).map(Book::getSource)
				.collect(Collectors.toSet());
		return "redirect:/hero/traits?sort=name,asc";
	}

	@GetMapping(params = "source")
	public String filterBySourceBook(Model model, String sort, @RequestParam String[] source, Pageable page) {
		sources = new HashSet<>(Arrays.asList(source));
		return "redirect:/hero/traits?sort=name,asc";
	}

	private Specification<Trait> byName() {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("requirement"), "%" + search + "%"));
	}

	private Specification<Trait> byAbility() {
		return (root, query, cb) -> {
			Join<Trait, AbilityType> abilities = root.join("abilities", JoinType.LEFT);
			return abilities.in(selectedAbilities);
		};
	}
	
	private Specification<Trait> bySkill() {
		return (root, query, cb) -> {
			Join<Trait, AbilityType> abilities = root.join("skills", JoinType.LEFT);
			return abilities.in(selectedSkills);
		};
	}
	
	private Specification<Trait> bySource() {
		return (root, query, cb) -> {
			Join<Book, Trait> traits = root.join("book", JoinType.LEFT);
			return cb.and(traits.get("source").in(sources));
		};
	}
}
