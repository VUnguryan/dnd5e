package com.dnd5e.wiki.controller.rest.model.api.gods;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;
import com.dnd5e.wiki.model.gods.GodSex;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GodInfoApi {
	private Integer id;
	private String name;
	private String englishName;
	private List<String> altNames;
	private String pantheon;
	private List<String> domains;
	private String symbol = "";
	private String title = "";
	private List<String> alignment;

	private List<String> entries;
	private String source;
	
	public GodInfoApi(God god){
		this.id = god.getId();
		this.name = god.getName();
		this.englishName = god.getEnglishName();
		this.title = "{@godrank" +  god.getSex().name().toLowerCase() + " " +god.getRank().name().toLowerCase() +"} " + god.getCommitment().trim(); 
		this.alignment = GodApi.getAligments(god);
		this.symbol = god.getSymbol();
		this.altNames = god.getNicknames() == null ? Collections.emptyList() : Arrays.asList(god.getNicknames().split("\\,"));
		this.domains = god.getDomains().stream().map(Domain::getCyrilicName).collect(Collectors.toList());
		this.pantheon = god.getPantheon().getName();
		this.entries = Arrays.asList(god.getDescription().split("\r"));
	}

	public static God build(GodInfoApi godInfoApi) {
		God god = new God();
		god.setName(godInfoApi.getName());
		god.setEnglishName(godInfoApi.getEnglishName());
		god.setDescription(String.join("\\r", godInfoApi.getEntries()));
		god.setNicknames(String.join(", ", godInfoApi.getAltNames()));
		god.setDomains(godInfoApi.getDomains().stream().map(Domain::valueOf).collect(Collectors.toList()));
		if (godInfoApi.getTitle().contains("@godrankmale")) {
			god.setSex(GodSex.MALE);
		} else if (godInfoApi.getTitle().contains("@godrankfemale")) {
			god.setSex(GodSex.MALE);
		} else if (godInfoApi.getTitle().contains("@godrankphilosophy")) {
			god.setSex(GodSex.MALE);
		} else {
			god.setSex(GodSex.UNDEFINE);
		}
		if (godInfoApi.getAlignment().contains("L")) {
			if (godInfoApi.getAlignment().contains("G")) {
				god.setAligment(Alignment.LAWFUL_GOOD);
			} else if (godInfoApi.getAlignment().contains("E")) {
				god.setAligment(Alignment.LAWFUL_EVIL);
			}
		} else if (godInfoApi.getAlignment().contains("C")) {
			if (godInfoApi.getAlignment().contains("G")) {
				god.setAligment(Alignment.CHAOTIC_GOOD);
			} else if (godInfoApi.getAlignment().contains("E")) {
				god.setAligment(Alignment.CHAOTIC_EVIL);
			}
			
		} else if (godInfoApi.getAlignment().contains("N")) {
			if (godInfoApi.getAlignment().contains("G")) {
				god.setAligment(Alignment.NEUTRAL_GOOD);
			} else if (godInfoApi.getAlignment().contains("E")) {
				god.setAligment(Alignment.NEUTRAL_EVIL);
			}
		}
		return god;
	}
}