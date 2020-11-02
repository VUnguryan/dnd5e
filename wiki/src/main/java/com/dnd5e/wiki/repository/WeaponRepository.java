package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.stock.Weapon;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Integer> {

}
