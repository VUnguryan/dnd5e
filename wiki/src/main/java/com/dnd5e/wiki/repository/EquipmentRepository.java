package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

	List<Equipment> findByNameContaining(String search);

}
