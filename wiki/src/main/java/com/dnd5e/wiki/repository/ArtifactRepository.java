package com.dnd5e.wiki.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dnd5e.wiki.model.artifact.Artifact;

public interface ArtifactRepository extends JpaRepository<Artifact, Integer>, JpaSpecificationExecutor<Artifact> {
	Page<Artifact> findByNameContaining(Pageable pagable, String paramString);
}
