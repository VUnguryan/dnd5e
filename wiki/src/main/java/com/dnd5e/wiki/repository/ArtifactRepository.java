package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.artifact.Artifact;
import com.dnd5e.wiki.model.artifact.ArtifactType;
import com.dnd5e.wiki.model.artifact.Rarity;

public interface ArtifactRepository extends JpaRepository<Artifact, Integer> {
	List<Artifact> findByNameContaining(String paramString);

	List<Artifact> findByTypeAndRarity(ArtifactType paramArtifactType, Rarity paramRarity);

	List<Artifact> findByType(ArtifactType paramArtifactType);

	List<Artifact> findByRarity(Rarity paramRarity);
}
