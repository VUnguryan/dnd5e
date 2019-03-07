package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.Weapon;

public interface WeaponRepository extends JpaRepository<Weapon, Integer> {

}
