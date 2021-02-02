package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.spell.GroupByCount;

@Repository
public abstract interface SpellRepository extends JpaRepository<Spell, Integer>, JpaSpecificationExecutor<Spell> {
	
	@Query("SELECT s FROM Spell s WHERE s.name = :name OR s.englishName = :name")
	Spell findOneByName(String name);

	List<Spell> findByLevelAndBook_type(Byte level, TypeBook type);

	@Query("SELECT s.distance FROM Spell s GROUP BY s.distance ORDER BY s.distance")
	List<String> findAllDistanceName();

	@Query("SELECT s FROM Spell s WHERE s.name LIKE %:name% OR englishName LIKE %:name% ")
	Page<Spell> findAllByName(@Param("name") String name, Pageable page);

	@Query("SELECT s.level AS field, COUNT(s.level) AS total FROM Spell s GROUP BY s.level ORDER BY s.level DESC")
	List<GroupByCount<Integer>> countTotalSpellByLevel();
	
	@Query("SELECT s.school AS field, COUNT(s.level) AS total FROM Spell s GROUP BY s.school")
	List<GroupByCount<MagicSchool>> countTotalSpellBySchool();
	
	@Query("SELECT c AS field, COUNT(c) AS total FROM Spell s JOIN s.heroClass AS c GROUP BY c")
	List<GroupByCount<HeroClass>> countTotalSpellByHeroClass();
	
	@Query("SELECT s.book AS field, COUNT(s.book) AS total FROM Spell s GROUP BY s.book")
	List<GroupByCount<Book>> countTotalSpellByBook();
	
	@Query("SELECT c AS field, COUNT(c) AS total FROM Spell s JOIN s.damageType AS c GROUP BY c")
	List<GroupByCount<DamageType>> countTotalSpellByTypeDamage();
}