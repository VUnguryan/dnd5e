package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.Trait;

@Repository
public interface TraitDataTablesRepository extends DataTablesRepository<Trait, Integer> {

}
