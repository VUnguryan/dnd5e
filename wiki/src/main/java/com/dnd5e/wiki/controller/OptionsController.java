package com.dnd5e.wiki.controller;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OptionsController {
	@GetMapping("/options")
	public String getTableOptions(Model model,Device device) {
		model.addAttribute("device", device);
		if (device.isMobile()) {
			return "datatable/options";
		}
		return "datatable/options2";
	}
}