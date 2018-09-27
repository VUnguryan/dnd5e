package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.SpellRepository;

@Controller
@RequestMapping({ "/spells" })
public class SpellController {
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

	@RequestMapping(method = RequestMethod.GET)
	public String getSpells(Model model) {
		model.addAttribute("order", Integer.valueOf(1));
		model.addAttribute("spells", spellRepository.findAll());
		return "spells";
	}

	@RequestMapping(method = RequestMethod.GET, params = "order")
	public String sortSpells(Model model, Integer order, String dir) {
		Sort sort = null;
		Sort.Direction direction = null;
		if (("asc".equals(dir)) || (dir == null)) {
			direction = Sort.Direction.ASC;
		} else {
			direction = Sort.Direction.DESC;
		}
		switch (order.intValue()) {
		case 0:
			sort = new Sort(direction, "level");
			break;
		case 1:
			sort = new Sort(direction, "name");
			break;
		case 2:
			sort = new Sort(direction, "school");
			break;
		default:
			sort = Sort.unsorted();
		}

		model.addAttribute("spells", spellRepository.findAll(sort));
		model.addAttribute("order", order);
		model.addAttribute("dir", dir);
		return "spells";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "search" })
	public String searchSpells(Model model, String search) {
		List<Spell> spells = spellRepository.findByNameAndEnglishNameContaining(search);
		if (spells.size() == 1) {
			return "redirect:spells/spell/" + spells.get(0).getId();
		}
		model.addAttribute("spells", spells);
		return "spells";
	}

	@RequestMapping(value = { "/add" }, method = { RequestMethod.GET })
	public String getSpellAddForm(Model model) {
		return "addSpell";
	}

	@RequestMapping(value = { "/spell/{id}" }, method = RequestMethod.GET)
	public String getSpell(Model model, @PathVariable Integer id) {
		Spell spell = spellRepository.findById(id).get();
		model.addAttribute("spell", spell);
		model.addAttribute("classes", classRepository.findBySpellName(spell.getName()));
		return "spellView";
	}

	@RequestMapping(value = { "/add" }, method = { RequestMethod.POST })
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
			if (spells!=null && !spells.isEmpty()) {
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
			if (levelAndSchool[1].contains("ритуал"))
			{
				spell.setRitual(true);
				levelAndSchool[1]=levelAndSchool[1].replace("ритуал", "");
			}
			else
			{
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
						spell.setAdditionalMaterialComponent(
								componetnsArray[i].trim().substring(3, componetnsArray[i].trim().length() - 1));
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
				}
				else 
				{
					sb.append(" ");
				}
				sb.append(text);
			}
			spell.setDescription(sb.toString());
			if (text != null) {
				sb = new StringBuilder(text.replace("На более высоких уровнях.", ""));
				while ((text = reader.readLine()) != null) {
					if (text.endsWith("-")) {
						text = text.substring(0, text.length() - 1);
					}
					else 
					{
						sb.append(" ");
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
}