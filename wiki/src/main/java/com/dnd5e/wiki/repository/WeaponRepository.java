package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.stock.Weapon;

public interface WeaponRepository extends JpaRepository<Weapon, Integer> {

}
