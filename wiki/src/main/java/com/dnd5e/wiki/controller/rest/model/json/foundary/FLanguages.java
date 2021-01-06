package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dnd5e.wiki.model.creature.Language;

import lombok.Getter;
import lombok.Setter;


/*
"cant",

"undercommon",
"",
"",
"",
"elvish"
*/
@Getter
@Setter
public class FLanguages {
	private Set<String> value = new HashSet<>();
	private String custom = "";
	public FLanguages(List<Language> languages) {
		for (Language language : languages) {
			switch (language.getName()) {
			case "язык Ааракокр":
				value.add("aarakocra");
				break;
			case "Ауран":
			case "понимает Ауран и Общий":
			case "Ауран и один язык по выбору создателя":
				value.add("aarakocra");
				break;
				
			case "понимает Великаний и Общий":
			case "Великаний":
			case "Гигантский (Великаний)":
				value.add("giant");
				break;
			case "Гитский":
			case "язык Гитов":
				value.add("gith");
				break;
				
			case "Глубинная Речь":
			case "Речь Глубин":
				value.add("deep");
				break;
			case "Гноллий":
				value.add("gnoll");
				break;
			case "Гномий":
				value.add("gnomish");
				break;
			case "Гоблинский":
				value.add("goblin");
				break;
			case "Дворфийский":
			case "Дварфийский":
				value.add("dwarvish");
				break;
			case "Бездны":
			case "Общий и язык Бездны":
			case "язык Бездны":
				value.add("abyssal");
				break;
			case "Драконий":
				value.add("draconic");
				break;
			case "Друидический":
				value.add("druidic");
				break;
			case "Инфернальный":
				value.add("infernal");
				break;
			case "Игнан":
				value.add("ignan");
				break;
				
			case "Небесный":
				value.add("celestial");
				break;
			case "Небесный и Первичный":
				value.add("celestial");
				value.add("primordial");
				break;
			case "Орочий":
				value.add("orc");
				break;
			case "Первичный":
				value.add("primordial");
				break;
			case "Акван":
				value.add("aquan");
				break;		
			case "язык Полуросликов":
				value.add("halfling");
				break;	
			case "Сильван":
				value.add("sylvan");
				break;				
			case "Сильван и Эльфийский":
			case "Эльфийский и Сильван":
				value.add("elvish");
				value.add("sylvan");
				break;
			case "Подземный":
			case "Подземья":
			case "и Подземный":
				value.add("terran");
				break;				
			case "Эльфийский и Подземный":
				value.add("terran");
				value.add("elvish");
				break;
			case "Эльфийский":
				value.add("elvish");
				break;	
				
			default:
				if (custom.isEmpty())
				{
					custom = language.getName();
				}
				else
				{
					custom += ", " + language.getName();
				}
				break;
			}
			if (language.getName().contains("Общий")) {
				value.add("common");
			}
		}
	}
}
