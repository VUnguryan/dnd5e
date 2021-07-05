package com.dnd5e.wiki.repository.datatable;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.spell.GroupByCount;
import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;

@Repository
public interface SpellDatatableRepository extends DataTablesRepository<Spell, Integer> {
	List<Spell> findByName(String paramString);

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
	
	@Query("SELECT s.book AS field, COUNT(s.book) AS total FROM Spell s WHERE s.book.type IN :types GROUP BY s.book")
	List<GroupByCount<Book>> countTotalSpellByBook(@Param("types") Set<TypeBook> types);
	
	@Query("SELECT c AS field, COUNT(c) AS total FROM Spell s JOIN s.damageType AS c GROUP BY c")
	List<GroupByCount<DamageType>> countTotalSpellByTypeDamage();

	@Query("SELECT s.concentration AS field, COUNT(s.concentration) AS total FROM Spell s GROUP BY s.concentration")
	List<GroupByCount<Boolean>> countTotalSpellByConcentration();

	@Query("SELECT s.ritual AS field, COUNT(s.ritual) AS total FROM Spell s GROUP BY s.ritual")
	List<GroupByCount<Boolean>> countTotalSpellByRitual();
}