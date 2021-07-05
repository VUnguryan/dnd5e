package com.dnd5e.wiki.controller;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entities/gods")
public class GodController {
	@GetMapping
	public String getGods(Model model, Device device)
	{
		model.addAttribute("device", device);
		if (device.isMobile()) {
			return "datatable/gods";
		}
		return "datatable/gods2";
	}
}