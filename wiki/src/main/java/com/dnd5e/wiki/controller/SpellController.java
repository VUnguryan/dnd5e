package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

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
import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.spell.TimeCast;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.SpellRepository;

@Controller
@RequestMapping({ "/spells" })
@Scope("session")
public class SpellController {
	private Optional<String> search = Optional.empty();
	private Optional<Integer> minLevel = Optional.empty();
	private Optional<Integer> maxLevel = Optional.empty();
	private Optional<Integer> classSelectedId = Optional.empty();
	private Optional<MagicSchool> schoolSelected = Optional.empty();
	private Optional<TimeCast> timeCastSelected = Optional.empty();
	private boolean verbal;
	private boolean somatic;
	private boolean material;

	private SpellRepository spellRepository;
	private ClassRepository classRepository;

	public SpellController() {
	}

	@Autowired
	public void setSpellRepository(SpellRepository spellRepository) {
		this.spellRepository = spellRepository;
	}

	@Autowired
	public void setClassRepository(ClassRepository classRepository) {
		this.classRepository = classRepository;
	}

	@GetMapping
	public String getSpells(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<Spell> specification = null;
		if (search.isPresent()) {
			specification = byName(search.get());
			model.addAttribute("spells", spellRepository.findAll(byName(search.get()), page));
		}
		if (classSelectedId.isPresent()) {
			if (specification == null) {
				specification = byClassId();
			} else {
				specification = Specification.where(specification).and(byClassId());
			}
		}
		if (schoolSelected.isPresent()) {
			if (specification == null) {
				specification = bySchool();
			} else {
				specification = Specification.where(specification).and(bySchool());
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
		if (verbal || somatic || material)
		{
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
		model.addAttribute("classSelected", classSelectedId);
		model.addAttribute("schoolSelected", schoolSelected);
		model.addAttribute("minLevel", minLevel);
		model.addAttribute("maxLevel", maxLevel);
		model.addAttribute("timeCast", timeCastSelected);
		model.addAttribute("timeCastTypes", TimeCast.values());
		model.addAttribute("verbal", verbal);
		model.addAttribute("somatic", somatic);
		model.addAttribute("material", material);
		model.addAttribute("filtered", search.isPresent() || minLevel.isPresent() || maxLevel.isPresent()
				|| classSelectedId.isPresent() || schoolSelected.isPresent() || timeCastSelected.isPresent() || verbal || somatic || material);
		return "spells";
	}

	@GetMapping(params = { "search" })
	public String searchSpells(Model model, String search) {
		if (search.isEmpty()) {
			this.search = Optional.empty();
		} else {
			this.search = Optional.of(search);
		}
		return "redirect:/spells?sort=level,asc";
	}

	@GetMapping(params = { "minLevel", "maxLevel" })
	public String filterSpellByLevels(Model model, String sort, Integer minLevel, Integer maxLevel, Pageable page) {
		this.minLevel = minLevel >= 0 ? Optional.of(minLevel) : Optional.empty();
		this.maxLevel = maxLevel >= 0 ? Optional.of(maxLevel) : Optional.empty();
		return "redirect:/spells?sort=" + sort;
	}

	@GetMapping(params = { "sort", "classType", "schoolType", "timeCast" })
	public String filterSpellByTypes(Model model, String sort, @RequestParam("classType") Integer classId,
			String schoolType, String timeCast, String verbal, String somatic, String material, Pageable page) {
		this.classSelectedId = classId == -1 ? Optional.empty() : Optional.of(classId);
		this.schoolSelected = "ALL".equals(schoolType) ? Optional.empty()
				: Optional.of(MagicSchool.valueOf(schoolType));
		this.timeCastSelected = "ALL".equals(timeCast) ? Optional.empty() : Optional.of(TimeCast.valueOf(timeCast));
		this.verbal = "on".equals(verbal);
		this.somatic = "on".equals(somatic);
		this.material = "on".equals(material);
		return "redirect:/spells?sort=" + sort;
	}

	@GetMapping("/spell/{id}")
	public String getSpell(Model model, @PathVariable Integer id) {
		Spell spell = spellRepository.findById(id).get();
		model.addAttribute("spell", spell);
		model.addAttribute("classes", classRepository.findBySpellName(spell.getName()));
		return "spellView";
	}

	//@GetMapping("/add")
	public String getSpellAddForm(Model model) {
		return "addSpell";
	}

	//@PostMapping("/add")
	public String addSpell(String spellText) {

		Spell spell = new Spell();
		try (LineNumberReader reader = new LineNumberReader(new StringReader(spellText))) {
			String header = reader.readLine();
			if (header.contains("[")) {
				int start = header.indexOf('[');
				int end = header.indexOf(']');
				String englishName = header.substring(start + 1, end);
				spell.setEnglishName(englishName);
				spell.setName(header.substring(0, start).trim().toUpperCase());
			} else {
				spell.setName(header.trim().toUpperCase());
			}
			List<Spell> spells = spellRepository.findByName(spell.getName());
			if (spells != null && !spells.isEmpty()) {
				spell = spells.get(0);
			}
			String text = reader.readLine();
			String[] levelAndSchool = text.split(",");
			if (levelAndSchool[0].startsWith("Заговор")) {
				spell.setLevel((short) 0);
			} else {
				short level = Short.valueOf(levelAndSchool[0].split(" ")[0]);
				spell.setLevel(level);
			}
			if (levelAndSchool[1].contains("ритуал")) {
				spell.setRitual(true);
				levelAndSchool[1] = levelAndSchool[1].replace("ритуал", "");
			} else {
				spell.setRitual(false);
			}
			spell.setSchool(MagicSchool.getMagicSchool(levelAndSchool[1].trim()));
			text = reader.readLine();
			spell.setTimeCast(text.split(":")[1].trim());
			text = reader.readLine();
			spell.setDistance(text.split(":")[1].trim());
			text = reader.readLine();
			String components = text.split(":")[1].trim();
			String[] componetnsArray = components.split(",");
			for (int i = 0; i < componetnsArray.length; i++) {
				if (componetnsArray[i].trim().equals("В")) {
					spell.setVerbalComponent(true);
				}
				if (componetnsArray[i].trim().equals("С")) {
					spell.setSomaticComponent(true);
				}
				if (componetnsArray[i].trim().startsWith("М")) {
					spell.setMaterialComponent(true);
					if (componetnsArray[i].trim().contains("(")) {
						String materials = "";
						componetnsArray[i] = componetnsArray[i].replace("М (", "");
						for (int j = i; j < componetnsArray.length; j++) {
							if (componetnsArray[j].trim().contains(")")) {
								materials += componetnsArray[j].trim().substring(0,
										componetnsArray[j].trim().length() - 1);
								break;
							} else {
								materials += componetnsArray[j].trim() + ", ";
							}

						}
						spell.setAdditionalMaterialComponent(materials);
					}
				}
			}
			text = reader.readLine();
			String duration = text.split(":")[1].trim();
			spell.setDuration(duration);
			StringBuilder sb = new StringBuilder();
			while ((text = reader.readLine()) != null && !text.startsWith("На более высоких уровнях.")) {
				if (text.endsWith("-")) {
					text = text.substring(0, text.length() - 1);
				} else {
					text += " ";
				}
				sb.append(text);
			}
			spell.setDescription(sb.toString());
			if (text != null) {
				if (text.endsWith("-")) {
					text = text.substring(0, text.length() - 1);
				}
				sb = new StringBuilder(text.replace("На более высоких уровнях.", ""));
				while ((text = reader.readLine()) != null) {
					if (text.endsWith("-")) {
						text = text.substring(0, text.length() - 1);
					} else {
						text += " ";
					}
					sb.append(text);
				}
				spell.setUpperLevel(sb.toString());
			}
			spellRepository.save(spell);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "addSpell";
	}

	private Specification<Spell> byName(String search) {
		return (root, query, cb) -> cb.and(cb.like(root.get("name"), "%" + search + "%"));
	}

	private Specification<Spell> bySchool() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("school"), schoolSelected.get()));
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

	private Specification<Spell> byClassId() {
		return (root, query, cb) -> {
			Join<HeroClass, Spell> hero = root.join("heroClass", JoinType.LEFT);
			return cb.equal(hero.get("id"), classSelectedId.get());
		};
	}

	private Specification<Spell> byComponents() {
		if (verbal && !somatic && !material) {
			return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")), cb.isFalse(root.get("somaticComponent")), cb.isFalse(root.get("materialComponent")));	
		}
		if (!verbal && somatic && !material) {
			return (root, query, cb) -> cb.and(cb.isFalse(root.get("verbalComponent")), cb.isTrue(root.get("somaticComponent")), cb.isFalse(root.get("materialComponent")));	
		}
		if (!verbal && !somatic && material) {
			return (root, query, cb) -> cb.and(cb.isFalse(root.get("verbalComponent")), cb.isFalse(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));	
		}
		
		if (verbal && somatic && !material) {
			return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")), cb.isTrue(root.get("somaticComponent")), cb.isFalse(root.get("materialComponent")));	
		}
		if (!verbal && somatic && material) {
			return (root, query, cb) -> cb.and(cb.isFalse(root.get("verbalComponent")), cb.isTrue(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));	
		}
		if (verbal && !somatic && material) {
			return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")), cb.isFalse(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));	
		}

		return (root, query, cb) -> cb.and(cb.isTrue(root.get("verbalComponent")), cb.isTrue(root.get("somaticComponent")), cb.isTrue(root.get("materialComponent")));	
	}
}