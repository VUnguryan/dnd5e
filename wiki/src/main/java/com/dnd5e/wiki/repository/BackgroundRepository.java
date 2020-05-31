package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.spell.GroupByCount;

public interface BackgroundRepository extends DataTablesRepository<Background, Integer>{
	List<Background> findAll();
	@Query("SELECT sk AS field, COUNT(sk) AS total FROM Background s JOIN s.skills AS sk GROUP BY sk")
	List<GroupByCount<SkillType>> countTotalSkill();
	
	@Query("SELECT b AS field, COUNT(b) AS total FROM Background s JOIN s.book AS b GROUP BY b")
	List<GroupByCount<Book>> countTotalBook();
}