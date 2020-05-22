package com.dnd5e.wiki.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureRace;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureTrait;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.creature.State;
import com.dnd5e.wiki.repository.BookRepository;
import com.dnd5e.wiki.repository.CreatureRaceRepository;
import com.dnd5e.wiki.repository.CreatureRepository;

@Controller
@RequestMapping({ "/creatures" })
@Scope("session")
public class CreatureController {
	@Autowired
	private CreatureRepository creatureRepo;
	
	@Autowired
	private CreatureRaceRepository creatureRaceRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private HttpSession session;
	
	private String search = "";
	private Optional<String> crMin = Optional.empty();
	private Optional<String> crMax = Optional.empty();
	private Optional<CreatureType> typeSelected = Optional.empty();
	private Optional<CreatureSize> sizeSelected = Optional.empty();
	private Set<HabitatType> habitatsSelected = EnumSet.allOf(HabitatType.class);

	private List<Book> books;
	private Set<String> selectedSources;
	private int sourceSize;
	
	private DamageType vulnerabilitySelected;
	private DamageType resistanceSelected;
	private DamageType immunitySelected;
	private State stateImmunitySelected;
	private ActionType actionTypeSelected;
	
	private String casterSelected;

	@PostConstruct
	public void initClassses() {
		this.selectedSources = bookRepo.finadAllByLeftJoinCreature()
				.stream()
				.filter(Objects::nonNull)
				.map(Book::getSource)
				.collect(Collectors.toSet());
		books = bookRepo.finadAllByLeftJoinCreature().stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(Book::getType))
				.collect(Collectors.toList());
		this.sourceSize = selectedSources.size(); 
	}

	@GetMapping
	public String getCreatures(Model model, @PageableDefault(size = 12, sort = "exp") Pageable page) {
		Specification<Creature> specification = null;
		if (!search.isEmpty()) {
			specification = byName();
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
		if (typeSelected.isPresent()) {
			specification = (specification == null) ? byType() : Specification.where(specification).and(byType());
		}
		if (crMin.isPresent()) {
			specification = (specification == null) ? byMinExp() : Specification.where(specification).and(byMinExp());
		}
		if (crMax.isPresent()) {
			specification = (specification == null) ? byMaxExp() : Specification.where(specification).and(byMaxExp());
		}
		if (sizeSelected.isPresent()) {
			specification = (specification == null) ? bySize() : Specification.where(specification).and(bySize());
		}
		if (!selectedSources.isEmpty()) {
			if (specification == null) {
				specification = bySource();
			} else {
				specification = Specification.where(specification).and(bySource());
			}
		}
		if (habitatsSelected.size() != HabitatType.values().length) {
			if (specification == null) {
				specification = byHabitatType();
			} else {
				specification = Specification.where(specification).and(byHabitatType());
			}
		}
		if (vulnerabilitySelected != null) {
			if (specification == null) {
				specification = byVulnerability();
			} else {
				specification = Specification.where(specification).and(byVulnerability());
			}
		}
		if (resistanceSelected != null) {
			if (specification == null) {
				specification = byResistance();
			} else {
				specification = Specification.where(specification).and(byResistance());
			}
		}
		if (immunitySelected != null) {
			if (specification == null) {
				specification = byImmunity();
			} else {
				specification = Specification.where(specification).and(byImmunity());
			}
		}
		if (stateImmunitySelected != null) {
			if (specification == null) {
				specification = byStateImmunity();
			} else {
				specification = Specification.where(specification).and(byStateImmunity());
			}
		}
		if (actionTypeSelected != null) {
			if (specification == null) {
				specification = byAction();
			} else {
				specification = Specification.where(specification).and(byAction());
			}
		}
		if (casterSelected != null) {
			if (specification == null) {
				specification = byCaster();
			} else {
				specification = Specification.where(specification).and(byCaster());
			}
		}
		model.addAttribute("creatures", creatureRepo.findAll(specification, page));
		model.addAttribute("filtered",
				crMin.isPresent() 
				|| crMax.isPresent() 
				|| typeSelected.isPresent() 
				|| sizeSelected.isPresent()
				|| selectedSources.size() != sourceSize || habitatsSelected.size() != HabitatType.values().length
				|| vulnerabilitySelected != null || resistanceSelected != null || immunitySelected != null || stateImmunitySelected != null
				|| actionTypeSelected != null || casterSelected != null);
		model.addAttribute("searchText", search);
		model.addAttribute("crMin", crMin);
		model.addAttribute("crMax", crMax);
		model.addAttribute("types", CreatureType.values());
		model.addAttribute("sizes", CreatureSize.values());
		model.addAttribute("habitats", HabitatType.values());
		model.addAttribute("typeSelected", typeSelected);
		model.addAttribute("sizeSelected", sizeSelected);
		model.addAttribute("habitatsSelected", habitatsSelected);
		
		model.addAttribute("vulnerabilitySelected", vulnerabilitySelected);
		model.addAttribute("vulnerabilities", DamageType.getVulnerability());
		model.addAttribute("resistanceSelected", resistanceSelected);
		model.addAttribute("resistance", DamageType.getResistance());
		model.addAttribute("immunitySelected", immunitySelected);
		model.addAttribute("immunities", DamageType.getImmunity());
		model.addAttribute("stateImmunitySelected", stateImmunitySelected);
		model.addAttribute("stateImmunities", State.getImmunity());

		
		model.addAttribute("actionTypeSelected", actionTypeSelected);
		model.addAttribute("actionTypes", ActionType.values());
		
		model.addAttribute("casterSelected", casterSelected);
		
		model.addAttribute("books", books);
		model.addAttribute("selectedBooks", selectedSources);
		
		return "creatures";
	}

	@GetMapping("/creature/{id}")
	public String getCreaturView(Model model, @PathVariable Integer id) {
		Creature creature = creatureRepo.findById(id).get();
		model.addAttribute("creature", creature);
		List<Action> actions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION)
				.collect(Collectors.toList());
		model.addAttribute("actions", actions);
		List<Action> reactions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION)
				.collect(Collectors.toList());
		model.addAttribute("reactions", reactions);
		List<Action> legendary = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY)
				.collect(Collectors.toList());
		model.addAttribute("legendary", legendary);
		return "creatureView";
	}

	@GetMapping("/creature/classic/{id}")
	public String getClassicCreature(Model model, @PathVariable Integer id) {
		Creature creature = creatureRepo.findById(id).get();
		model.addAttribute("creature", creature);
		List<Action> actions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION)
				.collect(Collectors.toList());
		model.addAttribute("actions", actions);
		List<Action> reactions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION)
				.collect(Collectors.toList());
		model.addAttribute("reactions", reactions);
		List<Action> legendary = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY)
				.collect(Collectors.toList());
		model.addAttribute("legendary", legendary);
		return "classicCreatureView";
	}

	@GetMapping("/race/{id}")
	public String getCreatureRace(Model model, @PathVariable Integer id) {
		CreatureRace race = creatureRaceRepo.getOne(id);
		model.addAttribute("creatureRaces", creatureRaceRepo.findAll(Sort.by("name")));
		model.addAttribute("race", race);
		model.addAttribute("creatures", creatureRepo.findAllByRaceIdOrderByExpAsc(id));
		return "classesCreature";
	}

	@GetMapping(params = "search")
	public String searchCreature(Model model, String sort, String search) {
		this.search = search.trim();
		return "redirect:/creatures?sort=exp,asc";
	}

	@GetMapping(params = "crMin")
	public String filterCrMin(Model model, String sort, String crMin) {
		this.crMin = "-1".equals(crMin) ? Optional.empty() : Optional.of(crMin);
		return "redirect:/creatures?sort=" + sort;
	}

	@GetMapping(params = "crMax")
	public String filterCrMax(Model model, String sort,  String crMax) {
		this.crMax = "-1".equals(crMax) ? Optional.empty() : Optional.of(crMax);
		return "redirect:/creatures?sort=" + sort;
	}

	@GetMapping(params = "type")
	public String filterType(Model model, String sort,  String type) {
		this.typeSelected = "ALL".equals(type) ? Optional.empty() : Optional.of(CreatureType.valueOf(type));
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = "cSize")
	public String filterSize(Model model, String sort,  String cSize) {
		this.sizeSelected = "ALL".equals(cSize) ? Optional.empty() : Optional.of(CreatureSize.valueOf(cSize));
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = "vulnerability")
	public String filterVulnerability(Model model, String sort, String vulnerability) {
		this.vulnerabilitySelected = "ALL".equals(vulnerability) ? null : DamageType.valueOf(vulnerability);
		return "redirect:/creatures?sort=" + sort;
	}

	@GetMapping(params = "resistance")
	public String filterResistance(Model model, String sort, String resistance) {
		this.resistanceSelected = "ALL".equals(resistance) ? null : DamageType.valueOf(resistance) ;
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = "immunity")
	public String filterImmunity(Model model, String sort, String immunity) {
		this.immunitySelected = "ALL".equals(immunity) ? null : DamageType.valueOf(immunity) ;
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = "stateImmunity")
	public String filterStateImmunity(Model model, String sort, String stateImmunity) {
		this.stateImmunitySelected = "ALL".equals(stateImmunity) ? null : State.valueOf(stateImmunity);
		return "redirect:/creatures?sort=" + sort;
	}

	@GetMapping(params = "action")
	public String filterLegendary(Model model, String sort, String action) {
		this.actionTypeSelected = "ALL".equals(action) ? null : ActionType.valueOf(action);
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = "caster")
	public String filterCaster(Model model, String sort, String caster) {
		switch (caster) {
		case "innateWitchcraft":
			this.casterSelected = "Врожденное Колдовство";
			break;
		case "useOfSpells":
			this.casterSelected = "Использование заклинаний";
			break;
		case "witchcraft":
			this.casterSelected = "Колдовство";
			break;
		default:
			this.casterSelected = null;
		}
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = { "clear" })
	public String cleaarFilters() {
		this.search = "";
		this.crMin = Optional.empty();
		this.crMax = Optional.empty();
		this.sizeSelected = Optional.empty();
		this.typeSelected = Optional.empty();
		this.selectedSources = bookRepo.finadAllByLeftJoinCreature()
				.stream()
				.filter(Objects::nonNull)
				.map(Book::getSource)
				.collect(Collectors.toSet());
		habitatsSelected = EnumSet.allOf(HabitatType.class);
		vulnerabilitySelected = null;
		resistanceSelected = null;
		immunitySelected = null;
		stateImmunitySelected = null;
		
		actionTypeSelected = null;
		casterSelected = null;
		return "redirect:/creatures?sort=exp,asc";
	}

	@GetMapping(params = "habitatType")
	public String filterByHabitatType(Model model, String sort, @RequestParam String[] habitatType, Pageable page) {
		habitatsSelected = Arrays.stream(habitatType).map(HabitatType::valueOf).collect(Collectors.toSet()); 
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = "source")
	public String filterBySourceBook(Model model, String sort, @RequestParam String[] source, Pageable page) {
		selectedSources = new HashSet<>(Arrays.asList(source));
		return "redirect:/creatures?sort=" + sort;
	}
	
	private Specification<Creature> byName() {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("englishName"), "%" + search + "%"));
	}

	private Specification<Creature> byType() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("type"), typeSelected.get()));
	}

	private Specification<Creature> byMinExp() {
		return (root, query, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get("exp"), toExp(crMin.get())));
	}

	private Specification<Creature> byMaxExp() {
		return (root, query, cb) -> cb.and(cb.lessThanOrEqualTo(root.get("exp"), toExp(crMax.get())));
	}

	private Specification<Creature> bySize() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("size"), sizeSelected.get()));
	}
	
	private Specification<Creature> byHabitatType() {
		return (root, query, cb) -> {
			Join<HabitatType, Creature> hero = root.join("habitates", JoinType.LEFT);
			return hero.in(habitatsSelected);
		};	
	}
	
	private Specification<Creature> byVulnerability(){
		return (root, query, cb) -> {
			Join<DamageType, Creature> join = root.join("vulnerabilityDamages");
			return join.in(vulnerabilitySelected);
		};
	}
	
	private Specification<Creature> byResistance(){
		return (root, query, cb) -> {
			Join<DamageType, Creature> join = root.join("resistanceDamages");
			return join.in(resistanceSelected);
		};
	}

	private Specification<Creature> byImmunity(){
		return (root, query, cb) -> {
			Join<DamageType, Creature> join = root.join("immunityDamages");
			return join.in(immunitySelected);
		};
	}

	private Specification<Creature> byStateImmunity(){
		return (root, query, cb) -> {
			Join<State, Creature> join = root.join("immunityStates");
			return join.in(stateImmunitySelected);
		};
	}
	
	private Specification<Creature> byAction(){
		return (root, query, cb) -> {
			Join<Action, Creature> join = root.join("actions");
			query.distinct(true);
			return cb.equal(join.get("actionType"), actionTypeSelected);
		};
	}
	
	private Specification<Creature> byCaster(){
		return (root, query, cb) -> {
			Join<CreatureTrait, Creature> join = root.join("feats");
			query.distinct(true);
			return cb.like(join.get("name"), "%"+casterSelected+"%");
		};
	}

	private Specification<Creature> byOfficial() {
		return (root, query, cb) -> {
			Join<Book, Creature> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}

	private Specification<Creature> bySource() {
		return (root, query, cb) -> {
			Join<Book, Creature> book = root.join("book", JoinType.LEFT);
			return cb.and(book.get("source").in(selectedSources));
		};	
	}

	private static int toExp(String cr) {
		switch (cr) {
		case "0":
			return 10;
		case "1/8":
			return 25;
		case "1/4":
			return 50;
		case "1/2":
			return 100;
		case "1":
			return 200;
		case "2":
			return 450;
		case "3":
			return 700;
		case "4":
			return 1100;
		case "5":
			return 1800;
		case "6":
			return 2300;
		case "7":
			return 2900;
		case "8":
			return 3900;
		case "9":
			return 5000;
		case "10":
			return 5900;
		case "11":
			return 7200;
		case "12":
			return 8400;
		case "13":
			return 10000;
		case "14":
			return 11500;
		case "15":
			return 13000;
		case "16":
			return 15000;
		case "17":
			return 18000;
		case "18":
			return 20000;
		case "19":
			return 22000;
		case "20":
			return 25000;
		case "21":
			return 25000;
		case "22":
			return 41000;
		case "23":
			return 50000;
		case "24":
			return 62000;
		case "25":
			return 75000;
		case "26":
			return 90000;
		case "27":
			return 105000;
		case "28":
			return 120000;
		case "29":
			return 135000;
		case "30":
			return 155000;
		}
		return 0;
	}
}