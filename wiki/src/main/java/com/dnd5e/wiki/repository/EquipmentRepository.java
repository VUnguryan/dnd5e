package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.spell.GroupByCount;
import com.dnd5e.wiki.model.stock.Equipment;
import com.dnd5e.wiki.model.stock.EquipmentType;

@Repository
public interface EquipmentRepository extends DataTablesRepository<Equipment, Integer> {
	List<Equipment> findAll();
	Page<Equipment> findByNameContaining(Pageable page, String search);
	List<Equipment> findByNameContaining(String search);

	@Query("SELECT s.type AS field, COUNT(s.type) AS total FROM Equipment s GROUP BY s.type")
	List<GroupByCount<EquipmentType>> countTotalEquipmentByType();

	@Query("SELECT s.book AS field, COUNT(s.book) AS total FROM Equipment s GROUP BY s.book")
	List<GroupByCount<Book>> countTotalEquipmentByBook();
}