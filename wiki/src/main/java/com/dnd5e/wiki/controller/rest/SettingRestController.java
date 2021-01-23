package com.dnd5e.wiki.controller.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.user.Setting;

@RestController
public class SettingRestController {
	public static final String SETTINGS = "settings";

	@Autowired
	private HttpSession session;

	@GetMapping("/settings")
	public Setting getSetting() {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		return setting;
	}

	@PostMapping("/settings/base")
	public void changeBaseRule(boolean ruleSetting) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setBaseRule(ruleSetting);
		session.setAttribute(SETTINGS, setting);
	}

	@PostMapping("/settings/home")
	public void changeHomeRule(boolean ruleSetting) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setHomeRule(ruleSetting);
		session.setAttribute(SETTINGS, setting);
	}

	@PostMapping("/settings/module")
	public void changeModule(boolean ruleSetting) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setModule(ruleSetting);
		session.setAttribute(SETTINGS, setting);
	}

	@PostMapping("/settings/setting")
	public void changeSetting(boolean ruleSetting) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setSetting(ruleSetting);
		session.setAttribute(SETTINGS, setting);
	}
}