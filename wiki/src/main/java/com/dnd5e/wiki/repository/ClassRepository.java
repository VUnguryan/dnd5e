package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.classes.HeroClass;

@Repository
public abstract interface ClassRepository extends JpaRepository<HeroClass, Integer> {
	@Query("select h from HeroClass h inner join h.spells s where s.name = :spellName")
	List<HeroClass> findBySpellName(@Param("spellName") String paramString);

	HeroClass findByEnglishName(String name);

	@Query("SELECT c FROM HeroClass c WHERE c.book.type IN :types")
	List<HeroClass> findAllBySources(@Param("types") Iterable<TypeBook> types);
}