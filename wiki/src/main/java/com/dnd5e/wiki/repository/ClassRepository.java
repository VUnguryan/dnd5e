package com.dnd5e.wiki.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.HeroClass;

@Repository
public abstract interface ClassRepository
  extends JpaRepository<HeroClass, Integer>
{
  @Query("select h from HeroClass h inner join h.spells s where s.name = :spellName")
  public abstract List<HeroClass> findBySpellName(@Param("spellName") String paramString);
  
  public abstract List<HeroClass> findByNameContaining(String paramString);
}