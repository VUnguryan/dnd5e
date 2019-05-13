package com.dnd5e.wiki.service;

public interface SecurityService {
	String findLoggedInUsername();
	void autologin(String name, String password);
}
