package com.dnd5e.wiki.controller.rest.model.api;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntriesHigherLevelApi {
	private String type;
	private String name;
	private List<String> entries;
}