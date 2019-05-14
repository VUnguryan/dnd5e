package com.dnd5e.wiki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
