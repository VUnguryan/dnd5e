package com.dnd5e.wiki.controller.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
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
	public String getSetting(HttpServletRequest req) {
		Setting settings = (Setting) session.getAttribute(SETTINGS);
		if (settings == null) {
			settings = new Setting();
			for (Cookie c : req.getCookies()) {
				if (c.getName().equals("base")) {
					settings.setBaseRule(Boolean.getBoolean(c.getValue()));
				}
				if (c.getName().equals("home")) {
					settings.setHomeRule(Boolean.getBoolean(c.getValue()));
				}
				if (c.getName().equals("module")) {
					settings.setModule(Boolean.getBoolean(c.getValue()));
				}
				if (c.getName().equals("setting")) {
					settings.setSetting(Boolean.getBoolean(c.getValue()));
				}
			}
		}
		return "OK";
	}

	@PostMapping("/settings/base")
	public void changeBaseRule(boolean ruleSetting, HttpServletResponse response) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setBaseRule(ruleSetting);
		session.setAttribute(SETTINGS, setting);
		Cookie cookie = new Cookie("base", Boolean.toString(ruleSetting));
		response.addCookie(cookie);
	}

	@PostMapping("/settings/home")
	public void changeHomeRule(boolean ruleSetting, HttpServletResponse response) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setHomeRule(ruleSetting);
		session.setAttribute(SETTINGS, setting);
		Cookie cookie = new Cookie("home", Boolean.toString(ruleSetting));
		response.addCookie(cookie);
	}

	@PostMapping("/settings/module")
	public void changeModule(boolean ruleSetting, HttpServletResponse response) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setModule(ruleSetting);
		session.setAttribute(SETTINGS, setting);
		Cookie cookie = new Cookie("module", Boolean.toString(ruleSetting));
		response.addCookie(cookie);
	}

	@PostMapping("/settings/setting")
	public void changeSetting(boolean ruleSetting, HttpServletResponse response) {
		Setting setting = (Setting) session.getAttribute(SETTINGS);
		if (setting == null) {
			setting = new Setting();
		}
		setting.setSetting(ruleSetting);
		session.setAttribute(SETTINGS, setting);
		Cookie cookie = new Cookie("setting", Boolean.toString(ruleSetting));
		response.addCookie(cookie);
	}
}