package com.dnd5e.wiki.controller;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.ArchitypeDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.ArchetypeSpellRepository;
import com.dnd5e.wiki.repository.SpellRepository;
import com.dnd5e.wiki.util.SourceUtil;

@Controller
@RequestMapping({ "/hero/spells" })
public class SpellController {
	@Autowired
	private HttpSession session;

	@Autowired
	private SpellRepository spellRepo;

	@Autowired
	private ArchetypeSpellRepository aSpellRepo; 
	
	@Autowired
	private SpellRepository repo;
	
	@GetMapping
	public String getSpellTable(Model model, Device device) {
		model.addAttribute("order", "[[ 1, 'asc' ]]");
		if (device.isMobile()) {
			return "datatable/spells";	
		}
		return "datatable/spells2";
	}
	
	@GetMapping("/spell/{spell:\\d+}")
	public String getSpell(Model model, @PathVariable Spell spell) {
		model.addAttribute("spell", ResponseEntity.ok(spell).getBody());

		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		model.addAttribute("arhitypes", 
				aSpellRepo.findAllBySpellId(spell.getId(),
					SourceUtil.getSources(settings)).stream()
						.map(ArchitypeDto::new)
						.collect(Collectors.toList()));
		return "spellView";
	}
	
	@GetMapping("/{spellName}")
	public String getSpellByName(Model model, @PathVariable String spellName) {
		Spell spell = spellRepo.findOneByName(spellName.replace('_', ' '));
		model.addAttribute("spell", spell);

		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		model.addAttribute("arhitypes",
				aSpellRepo.findAllBySpellId(spell.getId(),
						SourceUtil.getSources(settings)).stream()
							.map(ArchitypeDto::new)
							.collect(Collectors.toList()));
		return "spellView";
	}

	//@PostMapping ("convert")
	//@ResponseBody
	public String convertToUrl(String spells) {
		Spell spell = new Spell();
		return Arrays.asList(spells.split(",")).stream()
				.map(String::trim)
				.peek(s -> spell.setEnglishName(s))
				.map(s -> repo.findOne(Example.of(spell)))
				.map(s -> s.orElse(null))
				.filter(Objects::nonNull)
				.map(s -> " <a href=\"/hero/spells/spell/"+s.getId()+"\">" + s.getName().toLowerCase() + " [" + s.getEnglishName().toLowerCase() + "]" + "</a>")
				.collect(Collectors.joining(", "));
	}
}