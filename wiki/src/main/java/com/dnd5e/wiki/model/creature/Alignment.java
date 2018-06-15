package com.dnd5e.wiki.model.creature;

public enum Alignment {
	LAWFUL_GOOD("законно-добрый"),
	LAWFUL_NEUTRAL("законно-нейтральный"),
	LAWFUL_EVIL("законно-злой"),
	NEUTRAL_GOOD("нейтрально-добрый"),
	TRUE_NEUTRAL("законно-нейтральный"),
	NEUTRAL_EVIL("нейтрально-злой"),
	CHAOTIC_GOOD("хаотично-добрый"),
	CHAOTIC_NEUTRAL("хаотично-нейтральный"),
	CHAOTIC_EVIL("хаотично-злой"),
	WITHOUT("без мировоззрения");
	
	private String cytilicName;
	Alignment(String cyrilicName){
		this.cytilicName = cyrilicName;
	}
	public String getCytilicName() {
		return cytilicName;
	}
}
