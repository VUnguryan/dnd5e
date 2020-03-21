package com.dnd5e.wiki.controller.generator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.hero.race.Sex;
import com.dnd5e.wiki.model.tavern.Atmosphere;
import com.dnd5e.wiki.model.tavern.RandomEvent;
import com.dnd5e.wiki.model.tavern.TavernaCategory;
import com.dnd5e.wiki.model.tavern.TavernaDish;
import com.dnd5e.wiki.model.tavern.TavernaDrink;
import com.dnd5e.wiki.model.tavern.TavernaName;
import com.dnd5e.wiki.model.tavern.TavernaPrefixName;
import com.dnd5e.wiki.model.tavern.TavernaType;
import com.dnd5e.wiki.model.tavern.TopicDiscussed;
import com.dnd5e.wiki.model.tavern.Visitor;
import com.dnd5e.wiki.model.tavern.TavernaName.ObjectType;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.RaceRepository;
import com.dnd5e.wiki.repository.taverna.AtmosphereRepoditory;
import com.dnd5e.wiki.repository.taverna.RandomEventRepository;
import com.dnd5e.wiki.repository.taverna.TavernaDishRepository;
import com.dnd5e.wiki.repository.taverna.TavernaDrinkRepository;
import com.dnd5e.wiki.repository.taverna.TavernaNameRepository;
import com.dnd5e.wiki.repository.taverna.TavernaPrefixNameRepository;
import com.dnd5e.wiki.repository.taverna.TopicDiscussedRepository;
import com.dnd5e.wiki.repository.taverna.VisitorRepository;

@Controller
@RequestMapping("/calc/taverna")
@Scope("session")
public class TavernaController {
	private static final Random rnd = new Random();
	private static final List<HabitatType> habitates = EnumSet.of(HabitatType.SWAMP, HabitatType.CITY,
			HabitatType.MOUNTAIN, HabitatType.VILLAGE, HabitatType.UNDERGROUND, HabitatType.ARCTIC, HabitatType.WATERS,
			HabitatType.DESERT, HabitatType.GRASSLAND, HabitatType.FOREST, HabitatType.TROPICS).stream()
			.collect(Collectors.toList());
	@Autowired
	private TavernaNameRepository nameRepo;
	@Autowired
	private TavernaPrefixNameRepository prefixRepo;
	@Autowired
	private RaceRepository raceRepo;
	@Autowired
	private AtmosphereRepoditory atmosphereRepo;
	@Autowired
	private TopicDiscussedRepository topicRepo;
	@Autowired
	private RandomEventRepository eventRepo;
	@Autowired
	private VisitorRepository visitorRepo;
	@Autowired
	private ClassRepository classRepo;
	@Autowired
	private TavernaDishRepository dishRepo;
	@Autowired
	private TavernaDrinkRepository drinkRepo;

	private Set<String> generatedNames = new HashSet<>();

	private int[] ordinaryRaceId = { 1, 2, 3, 4, 6, 7, 8, 9, 26, 27, 28, 29, 31, 32, 33, 34 };

	@GetMapping
	public String getForm(Model model, String tavernaType, String tavernaCategory, String habitatType) {
		List<TavernaName> tavernaNames = nameRepo.findAll();
		List<TavernaPrefixName> prefixes = prefixRepo.findAll();
		TavernaType type;
		if (tavernaType == null) {
			type = TavernaType.values()[rnd.nextInt(TavernaType.values().length)];
		} else {
			type = TavernaType.valueOf(tavernaType);
		}

		int raceId = ordinaryRaceId[rnd.nextInt(ordinaryRaceId.length)];
		Race race = raceRepo.findById(raceId).get();
		String age = getAge();

		boolean men = rnd.nextBoolean();
		String sex = men ? " мужского пола" : " женского пола";
		String ownerDescription = age + race.getFullName().toLowerCase() + sex;
		String ownerName = "";
		if (race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE) != null) {
			List<String> names = new ArrayList<>(race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE));
			int indexName = rnd.nextInt(names.size());
			ownerDescription += " по имени " + names.get(indexName);
			ownerName = rnd.nextInt(10) == 0 ? names.get(indexName) : ownerName;
		}
		model.addAttribute("owner", StringUtils.capitalize(ownerDescription));
		String tavernName = null;
		do {
			int index = rnd.nextInt(tavernaNames.size());
			TavernaName tavernaName = tavernaNames.get(index);
			index = rnd.nextInt(prefixes.size());
			TavernaPrefixName prefix = prefixes.get(index);
			if (prefix.getObjectType() != null) {
				tavernaNames = tavernaNames.stream().filter(n -> n.getObjectType() == prefix.getObjectType())
						.collect(Collectors.toList());
				index = rnd.nextInt(tavernaNames.size());
				tavernaName = tavernaNames.get(index);
			}
			int nameType = rnd.nextInt(100);
			if (nameType > 70) {
				index = rnd.nextInt(tavernaNames.size());
				TavernaName tavernaName2 = tavernaNames.get(index);
				tavernName = type.getName() + " \"" + tavernaName.getName() + " и " + tavernaName2.getName() + "\"";
			} else if (nameType > 60) {
				index = rnd.nextInt(tavernaNames.size());
				TavernaName tavernaName2 = tavernaNames.get(index);
				tavernName = type.getName() + " \"" + prefix.getName(tavernaName.getSex()) + " " + tavernaName.getName()
						+ " и " + tavernaName2.getName() + "\"";
			} else {
				tavernName = type.getName() + " \"" + prefix.getName(tavernaName.getSex()) + " " + tavernaName.getName()
						+ "\"";
			}
		} while (generatedNames.contains(tavernName));
		if (generatedNames.size() < 500) {
			generatedNames.add(tavernName);
		} else {
			generatedNames.clear();
		}
		model.addAttribute("name", StringUtils.capitalize(tavernName));
		TavernaCategory category;
		if (tavernaCategory == null || tavernaCategory.isEmpty()) {
			category = TavernaCategory.values()[rnd.nextInt(TavernaCategory.values().length)];
		} else {
			category = TavernaCategory.valueOf(tavernaCategory);
		}
		model.addAttribute("type", type);
		model.addAttribute("tavernCategoty", category);
		List<Visitor> visitors = visitorRepo.findByTavernaTypeAndTavernaCategory(type, category);
		List<String> visitorss = new ArrayList<String>();
		int visitorCount = getVisitorCount(type);
		for (int i = 0; i < visitorCount; i++) {
			List<Visitor> visit = visitors.stream().flatMap(v -> v.getChance().stream())
					.filter(c -> c.getChance() > rnd.nextInt(100)).map(c -> c.getVisitor()).distinct()
					.collect(Collectors.toList());
			if (!visit.isEmpty()) {
				visitorss.add(getVisitor(visit.get(rnd.nextInt(visit.size())).getName()));
			}
		}
		model.addAttribute("visitors", visitorss);
		List<Atmosphere> atmospheres = atmosphereRepo.findByVisitorsLessThanEqual(visitorCount);
		Atmosphere atmosphere = atmospheres.get(rnd.nextInt(atmospheres.size()));
		model.addAttribute("atmosphere", atmosphere);
		if (visitorCount > 0) {
			List<TopicDiscussed> topics = topicRepo.findByVisitorsLessThanEqual(visitorCount);
			model.addAttribute("topic", topics.get(rnd.nextInt(topics.size())));
		}
		if (rnd.nextInt(20) > 10) {
			List<RandomEvent> events = eventRepo.findByVisitorsLessThanEqual(visitorCount);
			model.addAttribute("event", events.get(rnd.nextInt(events.size())));
		}
		model.addAttribute("types", TavernaType.values());
		model.addAttribute("categories", TavernaCategory.values());
		model.addAttribute("habitates", habitates);
		HabitatType habitat;
		if (habitatType == null || habitatType.isEmpty()) {
			habitat = habitates.get(rnd.nextInt(habitates.size()));
		} else {
			habitat = HabitatType.valueOf(habitatType);
		}
		model.addAttribute("habitatType", habitat);
		List<TavernaDrink> drinks = drinkRepo.findByHabitat(habitat);
		List<TavernaDish> dishes = dishRepo.findByHabitat(HabitatType.ARCTIC);
		if (category == TavernaCategory.CHEAP) {
			dishes.addAll(dishRepo.findByCategory(category));
			drinks.addAll(drinkRepo.findByCategory(category));
		}
		switch (type) {
		case BEER:
			model.addAttribute("drink", drinks.get(rnd.nextInt(drinks.size())));

			break;
		case INN:
			model.addAttribute("drink", drinks.get(rnd.nextInt(drinks.size())));
			model.addAttribute("dish", dishes.get(rnd.nextInt(dishes.size())));
			break;
		case HOTEL:
			model.addAttribute("dish", dishes.get(rnd.nextInt(dishes.size())));
			break;
		}
		return "calc/taverna";
	}

	private int getVisitorCount(TavernaType type) {
		int roll = 1 + rnd.nextInt(20);
		if (roll < 3)
			return 0;
		if (roll < 8)
			return 1 + rnd.nextInt(8);
		if (roll < 11)
			return 1 + rnd.nextInt(6) + (type == TavernaType.INN ? +5 : type == TavernaType.HOTEL ? 10 : 0);
		if (roll < 16)
			return 1 + rnd.nextInt(8) + (type == TavernaType.INN ? +5 : type == TavernaType.HOTEL ? 10 : 0);
		if (roll < 19)
			return 1 + rnd.nextInt(10) + (type == TavernaType.INN ? +5 : type == TavernaType.HOTEL ? 10 : 0);
		return 1 + rnd.nextInt(10) + rnd.nextInt(10)
				+ (type == TavernaType.INN ? +10 : type == TavernaType.HOTEL ? 15 : 0);
	}

	private String getAge() {
		switch (rnd.nextInt(5)) {
		case 0:
			return "молодой ";
		case 1:
			return "средних лет ";
		case 2:
			return "пожилой  ";
		case 3:
			return "старый ";
		case 4:
			return "юный ";
		}
		return "";
	}

	private String getVisitor(String profession) {
		if ("Искатель приключений".equals(profession)) {
			List<HeroClass> classes = classRepo.findAll();
			HeroClass classHero = classes.get(rnd.nextInt(classes.size()));
			profession += " (" + classHero.getName().toLowerCase() + ")";
		}
		String visitor = profession;
		int raceId = ordinaryRaceId[rnd.nextInt(ordinaryRaceId.length)];
		Race race = raceRepo.findById(raceId).get();
		boolean men = rnd.nextBoolean();
		String sex = men ? " мужского пола" : " женского пола";
		String visitorName = "";
		if (race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE) != null) {
			List<String> names = new ArrayList<>(race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE));
			if (names.isEmpty()) {
				names = new ArrayList<>(race.getAllNames().get(Sex.UNISEX));
				sex = "";
			}
			int indexName = rnd.nextInt(names.size());
			visitorName += " по имени " + names.get(indexName);
		}
		return visitor + " " + race.getFullName().toLowerCase() + " " + sex + " " + visitorName;
	}
}
