package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.repository.CreatureRepository;

@RestController
public class EnglishNameController {

	private CreatureRepository repository;

	@Autowired
	public void setMonsterRepository(CreatureRepository repository) {
		this.repository = repository;
	}

	@RequestMapping(value = "/setname", method = RequestMethod.POST)
	public List<String> setName(@RequestBody String body) throws IOException {
		List<String> last = new ArrayList<>();
		LineNumberReader reader = new LineNumberReader(new StringReader(body));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] name = line.split("!");
			if (name.length == 2) {
				Creature creature = repository.findByName(name[1].trim());
				if (creature != null) {
					creature.setEnglishName(name[0].trim());
					repository.saveAndFlush(creature);
				}
				else
				{
					last.add(name[1]);
				}
			}

		}

		return last;
	}
}
