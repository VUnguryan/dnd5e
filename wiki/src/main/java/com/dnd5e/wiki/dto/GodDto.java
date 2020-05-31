package com.dnd5e.wiki.dto;

import java.util.stream.Collectors;

import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GodDto {
	private String name;
	private String englishName;
	private String aligment;
	private String commitment;
	private String sex;
		
	private String description;
	private String symbol;
	private String nicknames;
	private String domains;
	private String pantheon;
	private String rank;
	
	public GodDto(God god) {
		name = god.getName();
		englishName = god.getEnglishName();
		commitment = god.getCommitment();
		sex = god.getSex().getCyrilicName();
		aligment = god.getAligment().getShortName();
		nicknames = god.getNicknames();
		symbol = god.getSymbol();
		domains = god.getDomains().stream().map(Domain::getCyrilicName).collect(Collectors.joining(", "));
		pantheon = god.getPantheon().getName();
		description = god.getDescription() == null ? "": god.getDescription();
		rank = god.getRank().getName(god.getSex());
	}
}