package com.dnd5e.wiki.controller.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.user.Setting;

@RestController
public class SettingRestController {
	public static final String HOME_RULE= "settings";
	
    @Autowired 
    private HttpSession session;
    
    @GetMapping("/settings")
    public Setting getSetting() {
    	Setting setting = (Setting) session.getAttribute(HOME_RULE);
    	if (setting == null) {
    		setting = new Setting();
    	}
    	return setting;
    }
    
	@PostMapping("/settings")
	public void changeContent(boolean homeRule) {
		Setting setting = (Setting) session.getAttribute(HOME_RULE);
    	if (setting == null) {
    		setting = new Setting();
    	}
		session.setAttribute(HOME_RULE, setting);
		setting.setHomeRule(homeRule);
	}
}
