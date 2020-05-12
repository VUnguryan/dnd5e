package com.dnd5e.wiki.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpSession;

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

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.treasure.MagicThing;
import com.dnd5e.wiki.model.treasure.MagicThingType;
import com.dnd5e.wiki.model.treasure.Rarity;
import com.dnd5e.wiki.repository.ArtifactRepository;
import com.dnd5e.wiki.repository.BookRepository;

@Controller
@RequestMapping("/stock/artifacts")
@Scope("session")
public class MagicalThingsController {
	private Optional<String> search = Optional.empty();
	private Optional<Rarity> rarityFilter = Optional.empty();
	private Optional<MagicThingType> typeFilter = Optional.empty();

	private ArtifactRepository repository;

	private BookRepository bookRepository;

	private List<Book> sources;
	private Set<String> selectedSources;
	private int sourceSize;
	@Autowired
	private HttpSession session;
	

	@Autowired
	public void setRepository(ArtifactRepository repository) {
		this.repository = repository;
	}

	@Autowired
	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@PostConstruct
	public void initClassses() {
		List<Book> allSources = bookRepository.finadAllByLeftJoinMagicThings();
		this.selectedSources = allSources
				.stream()
				.filter(Objects::nonNull)
				.map(Book::getSource)
				.collect(Collectors.toSet());
		this.sources = allSources.stream().sorted(Comparator.comparing(Book::getType)).collect(Collectors.toList());
		this.sourceSize = selectedSources.size();
	}

	@GetMapping
	public String getArtifactes(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<MagicThing> specification = null;
		if (search.isPresent()) {
			specification = byName(search.get());
		}
		Setting setting = (Setting) session.getAttribute(SettingRestController.HOME_RULE);
		if (setting == null || !setting.isHomeRule())
		{
			if (specification == null) {
				specification = byOfficial();
			} else {
				specification = Specification.where(specification).and(byOfficial());
			}
		}
		if (typeFilter.isPresent()) {
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
		if (!selectedSources.isEmpty()) {
			if (specification == null) {
				specification = bySource();
			} else {
				specification = Specification.where(specification).and(bySource());
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
		model.addAttribute("artifactTypes", MagicThingType.values());
		model.addAttribute("order", Integer.valueOf(1));
		model.addAttribute("searchText", search.orElse(""));
		model.addAttribute("filtered", rarityFilter.isPresent() || typeFilter.isPresent() || selectedSources.size() != sourceSize);
		
		model.addAttribute("books", sources);
		model.addAttribute("selectedBooks", selectedSources);
		return "equipment/magicalThings";
	}

	@GetMapping(params = "search")
	public String searchArtifacts(Model model, String search) {
		if (search.isEmpty()) {
			this.search = Optional.empty();
		} else {
			this.search = Optional.of(search);
		}
		return "redirect:/stock/artifacts";
	}

	@GetMapping(params = { "sort", "type", "rarity" })
	public String filterArtifact(Model model, String sort, String type, String rarity) {
		if ("ALL".equals(type)) {
			this.typeFilter = Optional.empty();
		} else {
			MagicThingType typeSelected = MagicThingType.valueOf(type);
			this.typeFilter = Optional.of(typeSelected);
		}
		if ("ALL".equals(rarity)) {
			this.rarityFilter = Optional.empty();
		} else {
			Rarity raritySelected = Rarity.valueOf(rarity);
			this.rarityFilter = Optional.of(raritySelected);
		}
		return "redirect:/stock/artifacts?sort=" + sort;
	}

	@GetMapping(params = { "clear" })
	public String cleaarFilters() {
		this.search = Optional.empty();
		this.typeFilter = Optional.empty();
		this.rarityFilter = Optional.empty();
		this.selectedSources = bookRepository.finadAllByLeftJoinMagicThings()
				.stream()
				.filter(Objects::nonNull)
				.map(Book::getSource)
				.collect(Collectors.toSet());
		return "redirect:/stock/artifacts?sort=name,asc";
	}
	
	@GetMapping(params = "source")
	public String filterBySourceBook(Model model, String sort, @RequestParam String[] source, Pageable page) {
		selectedSources = new HashSet<>(Arrays.asList(source));
		return "redirect:/stock/artifacts?sort=" + sort;
	}

	private Specification<MagicThing> byName(String search) {
		return (root, query, cb) -> cb.and(cb.like(root.get("name"), "%" + search + "%"));
	}

	private Specification<MagicThing> byRarity() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("rarity"), rarityFilter.get()));
	}

	private Specification<MagicThing> byType() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("type"), typeFilter.get()));
	}
	
	private Specification<MagicThing> byOfficial() {
		return (root, query, cb) -> {
			Join<Book, MagicThing> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}
	
	private Specification<MagicThing> bySource() {
		return (root, query, cb) -> {
			Join<Book, MagicThing> hero = root.join("book", JoinType.LEFT);
			return cb.and(hero.get("source").in(selectedSources));
		};	
	}
}