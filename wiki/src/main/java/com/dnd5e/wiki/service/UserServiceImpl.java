package com.dnd5e.wiki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dnd5e.wiki.model.user.User;
import com.dnd5e.wiki.repository.RoleRepository;
import com.dnd5e.wiki.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepository.findAll());
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByName(username);
	}
}