package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.Condition;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Integer>, JpaSpecificationExecutor<Condition> {
}
