package com.dnd5e.wiki.controller;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/stock/equipments" })
public class EquipmentController {

	@GetMapping
	public String getAllEquipments(Model model, Device device)
	{
		model.addAttribute("device", device);
		if (device.isMobile()) {
			return "datatable/equipments";
		}
		return "datatable/equipments2";
	}
}