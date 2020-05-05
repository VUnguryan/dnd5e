package com.dnd5e.wiki.controller.generator;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.hero.LifeStyle;
import com.dnd5e.wiki.model.hero.madness.Madness;
import com.dnd5e.wiki.model.hero.madness.MadnessType;
import com.dnd5e.wiki.model.spell.WildMagic;
import com.dnd5e.wiki.model.treasure.Rarity;
import com.dnd5e.wiki.repository.MadnessRepository;
import com.dnd5e.wiki.repository.WildMagicRepository;

@Controller
@RequestMapping("/calc")
public class CalculatorController {
	public static final Random rnd = new Random();
	@Autowired
	private WildMagicRepository wildMagicRepo;
	@Autowired
	private MadnessRepository madnessRepo;
	
	@GetMapping("/idle")
	public String getIdleForm(Model model) {
		model.addAttribute("lifeStyles", LifeStyle.values());
		model.addAttribute("costs", new float[] { 0, 0.1f, 0.2f, 1, 2, 4, 10 });
		return "idle";
	}

	@GetMapping("/buyMagicThings")
	public String getBuyMagicThingsForm(Model model) {
		model.addAttribute("costs", new float[] { 0, 0.1f, 0.2f, 1, 2, 4, 10 });
		model.addAttribute("rarities", Rarity.values());
		return "buyMagicThings";
	}

	@GetMapping("/wildMagic")
	public String getWildMagicRandom(Model model) {
		List<WildMagic> magics = wildMagicRepo.findAll();
		if (!magics.isEmpty()) {
			model.addAttribute("wildMagic", magics.get(rnd.nextInt(magics.size())));
		}
		return "calc/wildMagic";
	}
	
	@GetMapping("/madness")
	public String madness(Model model, String typeMadness) {
		List<Madness> madnesses;
		if (typeMadness != null) {
			MadnessType madnessType = MadnessType.valueOf(typeMadness);
			madnesses = madnessRepo.findByMadnessType(madnessType);
		}
		else {
			madnesses = madnessRepo.findAll();	
		}
		if (!madnesses.isEmpty()) {
			model.addAttribute("types", MadnessType.values());
			Madness madness = madnesses.get(rnd.nextInt(madnesses.size()));
			model.addAttribute("selectedType", madness.getMadnessType().getCyrilicName());
			model.addAttribute("madness", madness);
		}
		return "calc/madness";
	}
}