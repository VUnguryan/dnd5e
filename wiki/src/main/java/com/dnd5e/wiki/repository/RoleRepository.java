package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
