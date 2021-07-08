package com.dnd5e.wiki.controller;

import javax.websocket.server.PathParam;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TraitControler {
	@GetMapping("/traits")
	public String getTableTraits(Model model,Device device) {
		model.addAttribute("device", device);
		if (device.isMobile()) {
			return "datatable/traits";
		}
		return "datatable/traits2";
	}
	
	@GetMapping("/traits/{name}")
	public String getTrait(Model model,Device device, @PathParam(value = "name") String name) {
		model.addAttribute("device", device);
		return "/hero/traitView";
	}
}