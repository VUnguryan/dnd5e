package com.dnd5e.wiki.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.hero.race.Sex;
import com.dnd5e.wiki.model.tavern.Atmosphere;
import com.dnd5e.wiki.model.tavern.TavernaName;
import com.dnd5e.wiki.model.tavern.TavernaPrefixName;
import com.dnd5e.wiki.repository.RaceRepository;
import com.dnd5e.wiki.repository.taverna.AtmosphereRepoditory;
import com.dnd5e.wiki.repository.taverna.TavernaNameRepository;
import com.dnd5e.wiki.repository.taverna.TavernaPrefixNameRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Controller
@RequestMapping("/calc/taverna")
@Scope("session")
public class TavernaController {
	private static final Random rnd = new Random();
	@Autowired
	private TavernaNameRepository nameRepo;
	@Autowired
	private TavernaPrefixNameRepository prefixRepo;
	@Autowired
	private RaceRepository raceRepo;
	@Autowired
	private AtmosphereRepoditory atmosphereRepo;

	private Set<String> generatedNames = new HashSet<>();

	private int[] ordinaryRaceId = { 1, 2, 3, 4, 6, 7, 8, 9, 26, 27, 28, 29, 31, 32, 33, 34 };

	@GetMapping
	public String getForm(Model model) {
		List<TavernaName> tavernaNames = nameRepo.findAll();
		List<TavernaPrefixName> prefixes = prefixRepo.findAll();

		Type type = Type.values()[rnd.nextInt(Type.values().length)];
		String resultName = null;
		do {
			int index = rnd.nextInt(tavernaNames.size());
			TavernaName tavernaName = tavernaNames.get(index);
			index = rnd.nextInt(prefixes.size());
			TavernaPrefixName prefix = prefixes.get(index);
			int nameType = rnd.nextInt(100);
			if (nameType > 70) {
				index = rnd.nextInt(tavernaNames.size());
				TavernaName tavernaName2 = tavernaNames.get(index);
				resultName = type.cyrilicName + " \"" + tavernaName.getName() + " и " + tavernaName2.getName() + "\"";
			} else if (nameType > 60) {
				index = rnd.nextInt(tavernaNames.size());
				TavernaName tavernaName2 = tavernaNames.get(index);
				resultName = type.cyrilicName + " \"" + prefix.getName()
						+ (tavernaName.getSex() == Sex.MALE ? prefix.getMale() : prefix.getFemale()) + " "
						+ tavernaName.getName() + " и " + tavernaName2.getName() + "\"";
			} else {
				resultName = type.cyrilicName + " \"" + prefix.getName()
						+ (tavernaName.getSex() == Sex.MALE ? prefix.getMale() : prefix.getFemale()) + " "
						+ tavernaName.getName() + "\"";
			}
		} while (generatedNames.contains(resultName));
		generatedNames.add(resultName);
		int raceId = ordinaryRaceId[rnd.nextInt(ordinaryRaceId.length)];
		Race race = raceRepo.findById(raceId).get();
		model.addAttribute("name", StringUtils.capitalize(resultName));
		String age = getAge();

		boolean men = rnd.nextBoolean();
		String sex = men ? " мужского пола" : " женского пола";
		String ownerName = age + race.getFullName().toLowerCase() + sex;
		if (race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE) != null) {
			List<String> names = new ArrayList<>(race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE));
			int indexName = rnd.nextInt(names.size());
			ownerName += " по имени " + names.get(indexName);
		}
		model.addAttribute("owner", StringUtils.capitalize(ownerName));

		Category category = Category.values()[rnd.nextInt(Category.values().length)];
		model.addAttribute("type", type);
		model.addAttribute("category", category.cyrilicName + " заведение");
		List<String> visitors = new ArrayList<String>();
		int visitorCount = getVisitorCount();
		for (int i = 0; i < visitorCount; i++) {
			visitors.add(getVisitor(category));
		}
		model.addAttribute("visitors", visitors);
		List<Atmosphere> atmospheres = atmosphereRepo.findAll();
		model.addAttribute("atmosphere", atmospheres.get(rnd.nextInt(atmospheres.size())));
		return "calc/taverna";
	}

	private int getVisitorCount() {
		int type = 1 + rnd.nextInt(20);
		if (type < 3)
			return 0;
		if (type < 8)
			return 1 + rnd.nextInt(8);
		if (type < 11)
			return 1 + rnd.nextInt(6) + 10;
		if (type < 16)
			return 1 + rnd.nextInt(8) + 10;
		if (type < 19)
			return 1 + rnd.nextInt(10) + 10;
		return 1 + rnd.nextInt(10) + rnd.nextInt(10) + 15;
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

	private String getVisitor(Category category) {
		String visitor = "";
		switch (category) {
		case cheap:
			visitor = getCheapVisitorType();
			break;
		case ordinary:
			visitor = getOrdinaryVisitorType();
			break;
		case expensive:
			visitor = getExpensiveVisitorType();
			break;
		default:
			break;
		}

		int raceId = ordinaryRaceId[rnd.nextInt(ordinaryRaceId.length)];
		Race race = raceRepo.findById(raceId).get();
		boolean men = rnd.nextBoolean();
		String sex = men ? " мужского пола" : " женского пола";
		String visitorName = "";
		if (race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE) != null) {
			List<String> names = new ArrayList<>(race.getAllNames().get(men ? Sex.MALE : Sex.FEMALE));
			int indexName = rnd.nextInt(names.size());
			visitorName += " по имени " + names.get(indexName);
		}
		return visitor + " " + race.getFullName().toLowerCase() + " " + sex + " " + visitorName;
	}

	private String getExpensiveVisitorType() {
		int type = 1 + rnd.nextInt(20);
		if (type < 2)
			return "Подозрительный тип";
		if (type < 7)
			return "Торговец";
		if (type == 7)
			return "Бард";
		if (type < 16)
			return "Дворянин";
		if (type == 16)
			return "Военная элита";
		if (type == 17)
			return "Жрец";
		if (type < 20)
			return "Искатель приключений";
		type = 1 + rnd.nextInt(4);
		switch (type) {
		case 1:
			return "Градоначальник";
		case 2:
			return "Криминальный авторитет";
		case 3:
			return "Принц";
		}
		return "Аристократ";
	}

	private String getOrdinaryVisitorType() {
		int type = 1 + rnd.nextInt(20);
		if (type < 3)
			return "Подозрительный тип";
		if (type > 2 && type < 7)
			return "Торговец";
		if (type == 7)
			return "Бард";
		if (type > 7 && type < 17)
			return "Обыватель";
		if (type > 16 && type < 19)
			return "Искатель приключений";
		type = 1 + rnd.nextInt(4);
		switch (type) {
		case 1:
			return "Жрец";
		case 2:
			return "Ветеран";
		case 3:
			return "Дворянин";
		}
		return "Мэр";
	}

	private String getCheapVisitorType() {
		int type = 1 + rnd.nextInt(20);
		if (type < 3)
			return "Стражник";
		if (type > 2 && type < 7)
			return "Нищий";
		if (type == 7)
			return "Бард";
		if (type > 7 && type < 13)
			return "Подозрительный тип";
		if (type > 7 && type < 13)
			return "Обыватель";
		return "Искатель приключений";
	}

	@AllArgsConstructor
	@Getter
	enum Category {
		cheap("Дешёвое "), ordinary("Обычное"), expensive("Дорогое");
		private String cyrilicName;

	}

	@AllArgsConstructor
	@Getter
	enum Type {
		tavern("Таверна"), inn("Постоялый двор"), pub("Трактир"), beer("Пивная"), hotel("Гостиница");
		private String cyrilicName;
	}
}
