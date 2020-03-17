package com.dnd5e.wiki.repository.taverna;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.tavern.TavernaName;

public interface TavernaNameRepository extends JpaRepository<TavernaName, Integer> {

}
