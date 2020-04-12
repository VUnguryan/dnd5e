package com.dnd5e.wiki.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dnd5e.wiki.model.treasure.MagicThing;

public interface ArtifactRepository extends JpaRepository<MagicThing, Integer>, JpaSpecificationExecutor<MagicThing> {
	Page<MagicThing> findByNameContaining(Pageable pagable, String paramString);
}
