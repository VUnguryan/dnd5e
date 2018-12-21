package com.dnd5e.wiki.service;

import com.dnd5e.wiki.model.user.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);
}
