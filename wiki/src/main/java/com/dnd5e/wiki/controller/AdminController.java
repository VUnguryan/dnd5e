package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.SpellRepository;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	private SpellRepository spellRepository;
	
	@GetMapping("/spell/add")
	public String getSpellAddForm(Model model) {
		return "addSpell";
	}

	@PostMapping("/spell/add")
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
				spell.setLevel((byte)0);
			} else {
				byte level = Byte.valueOf(levelAndSchool[0].split(" ")[0]);
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
}
