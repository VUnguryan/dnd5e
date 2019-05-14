package com.dnd5e.wiki.controller;

import java.sql.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dnd5e.wiki.dto.user.UserRegForm;
import com.dnd5e.wiki.model.user.User;
import com.dnd5e.wiki.service.SecurityService;
import com.dnd5e.wiki.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new UserRegForm());
		return "/user/registration";
	}

	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") UserRegForm userForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/user/registration";
		}
		if(userService.findByUsername(userForm.getName()).isPresent())
		{
			ObjectError error = new ObjectError("userExist", "Пользователь с таким именем уже зарегистрирован");
			bindingResult.addError(error);
			return "/user/registration";
		}
		userService.save(new User(userForm));
		securityService.autologin(userForm.getName(), userForm.getPasswordConfirm());
		return "redirect:/travel/tavern?sort=name,asc";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Ваше имя пользователя или пароль неверны.");
		}
		if (logout != null) {
			model.addAttribute("message", "Вы успешно вышли из системы.");
		}
		return "/user/login";
	}
}