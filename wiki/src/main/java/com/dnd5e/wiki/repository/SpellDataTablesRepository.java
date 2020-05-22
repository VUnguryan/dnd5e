package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.spell.Spell;

@Repository
public interface SpellDataTablesRepository extends DataTablesRepository<Spell, Integer> {

}
