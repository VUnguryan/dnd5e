package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.travel.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer>{

}
