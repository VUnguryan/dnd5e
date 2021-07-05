package com.dnd5e.wiki.controller;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hero/")
public class HeroController {
	@GetMapping("/conditions")
	public String getCondinons(Model model, Device device) {
		model.addAttribute("device", device);

		if (device.isMobile()) {
			return "datatable/conditions";
		}
		return "datatable/conditions2";
	}

	@GetMapping("/options")
	public String getTableOptions(Model model,Device device) {
		model.addAttribute("device", device);

		if (device.isMobile()) {
			return "datatable/options";
		}
		return "datatable/options2";
	}

	@GetMapping("/traits")
	public String getTableTraits(Model model,Device device) {
		model.addAttribute("device", device);

		if (device.isMobile()) {
			return "datatable/traits";
		}
		return "datatable/traits2";
	}
}