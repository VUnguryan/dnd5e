package com.dnd5e.wiki.repository.taverna;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.tavern.TopicDiscussed;

public interface TopicDiscussedRepository  extends JpaRepository<TopicDiscussed, Integer>  {
	List<TopicDiscussed> findByVisitorsLessThanEqual(int visitors);
}
