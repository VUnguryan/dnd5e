package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.stock.WeaponProperty;

@Repository
public interface WeaponPropertyRepository extends JpaRepository<WeaponProperty, Integer>{

}
