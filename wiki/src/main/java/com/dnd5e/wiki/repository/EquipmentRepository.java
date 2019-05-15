package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.stock.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
	Page<Equipment> findByNameContaining(Pageable page, String search);
	List<Equipment> findByNameContaining(String search);
}
