package com.dnd5e.wiki.controller.rest.model.api;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GodInfoApi {
	private String name;
	private String nicknames;
	private String commitment;
	private String rank;
	private String aligment;
	private String symbol;
	private List<String> domains;
	private String pantheon;
	private String description;
	
	public GodInfoApi(God god){
		this.name = god.getName();
		this.commitment = god.getCommitment();
		this.rank = god.getPrefixName();
		this.aligment = god.getAligment().getCyrilicName();
		this.symbol = god.getSymbol();
		this.nicknames = god.getNicknames();
		this.domains = god.getDomains().stream().map(Domain::getCyrilicName).collect(Collectors.toList());
		this.pantheon = god.getPantheon().getName();
		this.description = god.getDescription();
	}

}