package com.dnd5e.wiki.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dnd5e.wiki.model.Rule;

@Controller
public class RuleController {
	@GetMapping("/rules")
	public String getRules(Model model, Device device) {
		model.addAttribute("device", device);
		if (device.isMobile()) {
			return "datatable/rules";
		}
		return "datatable/rules2";
	}
	
	@GetMapping("/rule/{rule:\\d+}")
	public String getRule(Model model, @PathVariable Rule rule) {
		model.addAttribute("condition", ResponseEntity.ok(rule).getBody());
		return "ruleView";
	}
}