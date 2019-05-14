package com.dnd5e.wiki.service;

import java.util.Optional;

import com.dnd5e.wiki.model.user.User;

public interface UserService {
	void save(User user);

	Optional<User> findByUsername(String username);
}
