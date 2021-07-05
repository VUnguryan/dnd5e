package com.dnd5e.wiki.controller.rest.paging;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SearchPanes {
	private Map<String, List<Item>> options;
}