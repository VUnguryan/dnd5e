package com.dnd5e.wiki.controller.rest.model.api.gods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;

import lombok.Getter;

@Getter
public class GodApi {
	private Integer id;
	private String name;
	private String englishName;
	private List<String> altNames;
	private String pantheon;
	private List<String> domains;
	private String title = "";
	private String symbol = "";
	private List<String> alignment;

	private String source;
	public GodApi(God god) {
		this.id = god.getId();
		this.name = god.getName();
		this.englishName = god.getEnglishName();
		this.altNames = god.getNicknames() == null ? Collections.emptyList() : Arrays.asList(god.getNicknames().split("\\,"));
		this.title = "{@godrank" +  god.getSex().name().toLowerCase() + " " +god.getRank().name().toLowerCase() +"} " + god.getCommitment().trim();
		this.symbol = god.getSymbol();
		this.alignment = getAligments(god);
		this.domains = god.getDomains().stream().map(Domain::getCyrilicName).collect(Collectors.toList());
		this.pantheon = god.getPantheon().getName();
	}
	
	public static List<String> getAligments(God god) {
		List<String> alignments = new ArrayList<>(2);
		if (god.getAligment().name().contains("LAWFUL"))
		{
			alignments.add("L");
		}
		if (god.getAligment().name().contains("GOOD"))
		{
			alignments.add("G");
		}
		if (god.getAligment().name().contains("NEUTRAL"))
		{
			alignments.add("N");
		}
		if (god.getAligment().name().contains("CHAOTIC"))
		{
			alignments.add("C");
		}
		return alignments;
	}
}