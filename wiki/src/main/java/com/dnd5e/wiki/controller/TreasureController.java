package com.dnd5e.wiki.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.treasure.MagicThing;

@Controller
@RequestMapping("/stock")
public class TreasureController {
	@GetMapping("/artifacts")
	public String getMagicThings(Model model, Device device) {
		model.addAttribute("device", device);
		if (device.isMobile()) {
			return "datatable/magicThings";	
		}
		return "datatable/magicThings2";
	}
	
	@GetMapping("/artifacts/{magicThing}")
	public String getMagicThing(Model model, Device device, @PathVariable MagicThing magicThing) {
		model.addAttribute("device", device);
		model.addAttribute("magicThing", ResponseEntity.ok(magicThing).getBody());
		return "equipment/magicalThingView";
	}
	
	@GetMapping("/treasures")
	public String getTreasures(Model model, Device device) {
		model.addAttribute("device", device);
		return "datatable/treasures";
	}
}