package com.dnd5e.wiki.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.spell.Spell;

@Repository
public abstract interface SpellRepository
  extends JpaRepository<Spell, Integer>
{
  public abstract boolean existsByName(String paramString);
  
  public abstract List<Spell> findByName(String paramString);
  
  @Query("SELECT s FROM Spell s WHERE s.name LIKE %:searchTerm% OR s.englishName LIKE %:searchTerm%")
  public abstract List<Spell> findByNameAndEnglishNameContaining(@Param("searchTerm") String paramString);
}