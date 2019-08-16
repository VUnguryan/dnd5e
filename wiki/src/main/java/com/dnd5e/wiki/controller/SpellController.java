package com.dnd5e.wiki.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.spell.Component;
import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.spell.TimeCast;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.SpellRepository;

@Controller
@RequestMapping({ "/hero/spells" })
@Scope("session")
public class SpellController {
	private Optional<String> search = Optional.empty();
	private Optional<Integer> minLevel = Optional.empty();
	private Optional<Integer> maxLevel = Optional.empty();
	private Set<Integer> clases;
	private Set<MagicSchool> schools = EnumSet.allOf(MagicSchool.class);
	private Optional<TimeCast> timeCastSelected = Optional.empty();
	private Set<Component> components;
	private Set<String> distances;

	private SpellRepository spellRepository;
	private ClassRepository classRepository;
	private List<String> distanceTypes;

	@Autowired
	public void setSpellRepository(SpellRepository spellRepository) {
		this.spellRepository = spellRepository;
	}

	@Autowired
	public void setClassRepository(ClassRepository classRepository) {
		this.classRepository = classRepository;
	}

	public SpellController() {
	}

	@PostConstruct
	public void initClssses() {
		this.clases = classRepository.findAll().stream().map(HeroClass::getId).collect(Collectors.toSet());
		this.components = EnumSet.noneOf(Component.class);
		Comparator<String> comparator = (s1, s2) -> {
			//s1.split(" ");
			return s1.compareTo(s2);
		};
		this.distanceTypes = spellRepository.findAllDistanceName().stream()
				.sorted(comparator).collect(Collectors.toList());
		this.distances = new HashSet<>();
	}

	@GetMapping
	public String getSpells(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<Spell> specification = null;
		if (search.isPresent()) {
			specification = byName(search.get());
		}
		if (!clases.isEmpty() && clases.size() < classRepository.count()) {
			if (specification == null) {
				specification = byClassId();
			} else {
				specification = Specification.where(specification).and(byClassId());
			}
		}
		if (!schools.isEmpty()) {
			if (specification == null) {
				specification = bySchool();
			} else {
				specification = Specification.where(specification).and(bySchool());
			}
		}
		if (!distances.isEmpty()) {
			if (specification == null) {
				specification = byDistance();
			} else {
				specification = Specification.where(specification).and(byDistance());
			}
		}
		if (timeCastSelected.isPresent()) {
			if (specification == null) {
				specification = byTimeCast();
			} else {
				specification = Specification.where(specification).and(byTimeCast());
			}
		}
		if (minLevel.isPresent()) {
			if (specification == null) {
				specification = byMinLevel();
			} else {
				specification = Specification.where(specification).and(byMinLevel());
			}
		}
		if (maxLevel.isPresent()) {
			if (specification == null) {
				specification = byMaxLevel();
			} else {
				specification = Specification.where(specification).and(byMaxLevel());
			}
		}
		if (!components.isEmpty()) {
			if (specification == null) {
				specification = byComponents();
			} else {
				specification = Specification.where(specification).and(byComponents());
			}
		}
		if (specification == null) {
			model.addAttribute("spells", spellRepository.findAll(page));
		} else {
			model.addAttribute("spells", spellRepository.findAll(specification, page));
		}
		model.addAttribute("classTypes", classRepository.findAll());
		model.addAttribute("schoolTypes", MagicSchool.values());
		model.addAttribute("searchText", search);
		model.addAttribute("classSelected", clases);
		model.addAttribute("schoolSelected", schools);
		model.addAttribute("minLevel", minLevel);
		model.addAttribute("maxLevel", maxLevel);
		model.addAttribute("timeCast", timeCastSelected);
		model.addAttribute("timeCastTypes", TimeCast.values());
		model.addAttribute("components", components);
		model.addAttribute("componentNames", Component.values());
		model.addAttribute("componentNames", Component.values());
		model.addAttribute("distanceTypes", distanceTypes);
		model.addAttribute("distances", distances);

		model.addAttribute("filtered",
				search.isPresent() || minLevel.isPresent() || maxLevel.isPresent()
						|| clases.size() < classRepository.count() || (schools.size() != MagicSchool.values().length)
						|| timeCastSelected.isPresent() || !components.isEmpty() || !distances.isEmpty());
		return "spells";
	}

	@GetMapping(params = { "search" })
	public String searchSpells(Model model, String search) {
		if (search.isEmpty()) {
			this.search = Optional.empty();
		} else {
			this.search = Optional.of(search);
		}
		return "redirect:/hero/spells?sort=level,asc";
	}

	@GetMapping(params = { "clear" })
	public String cleaarFilters(Model model, String search) {
		this.search = Optional.empty();
		this.minLevel = Optional.empty();
		this.maxLevel = Optional.empty();
		this.clases = classRepository.findAll().stream().map(HeroClass::getId).collect(Collectors.toSet());
		this.schools = EnumSet.copyOf(Arrays.asList(MagicSchool.values()));
		this.timeCastSelected = Optional.empty();
		this.components = EnumSet.noneOf(Component.class);
		distances.clear();
		return "redirect:/hero/spells?sort=level,asc";
	}

	@GetMapping(params = { "minLevel", "maxLevel" })
	public String filterByLevels(Model model, String sort, Integer minLevel, Integer maxLevel, Pageable page) {
		this.minLevel = minLevel >= 0 ? Optional.of(minLevel) : Optional.empty();
		this.maxLevel = maxLevel >= 0 ? Optional.of(maxLevel) : Optional.empty();
		return "redirect:/hero/spells?sort=" + sort;
	}

	@GetMapping(params = "distance")
	public String filterByDistance(Model model, String sort, @RequestParam String[] distance, Pageable page) {
		distances = new HashSet<>(Arrays.asList(distance));
		return "redirect:/hero/spells?sort=" + sort;
	}

	@GetMapping(params = { "sort", "classType", "schoolType", "timeCast" })
	public String filterByTypes(Model model, String sort, @RequestParam("classType") Integer[] classId,
			String[] schoolType, String timeCast, String[] components, Pageable page) {
		clases = Arrays.stream(classId).collect(Collectors.toSet());
		schools = Arrays.stream(schoolType).map(MagicSchool::valueOf).collect(Collectors.toSet());
		timeCastSelected = "ALL".equals(timeCast) ? Optional.empty() : Optional.of(TimeCast.valueOf(timeCast));
		this.components = Arrays.stream(components).map(Component::valueOf).collect(Collectors.toSet());
		return "redirect:/hero/spells?sort=" + sort;
	}

	@GetMapping("/spell/{id}")
	public String getSpell(Model model, @PathVariable Integer id) {
		Spell spell = spellRepository.findById(id).get();
		model.addAttribute("spell", spell);
		model.addAttribute("classes", classRepository.findBySpellName(spell.getName()));
		return "spellView";
	}

	private Specification<Spell> byName(String search) {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("englishName"), "%" + search + "%"));
	}

	private Specification<Spell> bySchool() {
		return (root, query, cb) -> cb.and(root.get("school").in(schools));
	}

	private Specification<Spell> byTimeCast() {
		return (root, query, cb) -> cb.and(cb.like(root.get("timeCast"), timeCastSelected.get().getName() + "%"));
	}

	private Specification<Spell> byMinLevel() {
		return (root, query, cb) -> cb.and(cb.greaterThan(root.get("level"), minLevel.get() - 1));
	}

	private Specification<Spell> byMaxLevel() {
		return (root, query, cb) -> cb.and(cb.lessThan(root.get("level"), maxLevel.get() + 1));
	}

	private Specification<Spell> byDistance() {
		return (root, query, cb) -> cb.and(root.get("distance").in(distances));
	}

	private Specification<Spell> byClassId() {
		return (root, query, cb) -> {
			Join<HeroClass, Spell> hero = root.join("heroClass", JoinType.LEFT);
			return cb.and(hero.get("id").in(clases));
		};
	}

	private Specification<Spell> byComponents() {
		boolean verbal = components.contains(Component.VERBAL);
		boolean somatic = components.contains(Component.SOMATIC);
		boolean material = components.contains(Component.MATERIAL);
		if (verbal && !somatic && !material) {
			return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")),
					cb.isFalse(root.get("somaticComponent")), cb.isFalse(root.get("materialComponent")));
		}
		if (!verbal && somatic && !material) {
			return (root, query, cb) -> cb.and(cb.isFalse(root.get("verbalComponent")),
					cb.isTrue(root.get("somaticComponent")), cb.isFalse(root.get("materialComponent")));
		}
		if (!verbal && !somatic && material) {
			return (root, query, cb) -> cb.and(cb.isFalse(root.get("verbalComponent")),
					cb.isFalse(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));
		}

		if (verbal && somatic && !material) {
			return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")),
					cb.isTrue(root.get("somaticComponent")), cb.isFalse(root.get("materialComponent")));
		}
		if (!verbal && somatic && material) {
			return (root, query, cb) -> cb.and(cb.isFalse(root.get("verbalComponent")),
					cb.isTrue(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));
		}
		if (verbal && !somatic && material) {
			return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")),
					cb.isFalse(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));
		}
		return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")),
				cb.isTrue(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));
	}
}