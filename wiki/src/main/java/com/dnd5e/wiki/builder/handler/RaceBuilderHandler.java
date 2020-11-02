package com.dnd5e.wiki.builder.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.dnd5e.wiki.builder.model.HeroModel;
import com.dnd5e.wiki.builder.model.RaceInfo;
import com.dnd5e.wiki.dto.RaceDto;
import com.dnd5e.wiki.model.hero.AbilityBonus;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.repository.RaceRepository;

@Component
public class RaceBuilderHandler {
	@Autowired
	private RaceRepository raceRepo;

	@Transactional
	public void addRace(HeroModel heroModel, RaceInfo raceInfo) {
		raceInfo.clearBonuses();
		Race race = raceRepo.getOne(raceInfo.getId());
		for (AbilityBonus bonus : race.getAbilityValueBonuses()) {
			switch (bonus.getAbility()) {
			case STRENGTH:
				raceInfo.setStrength(bonus.getBonus());
				break;
			case DEXTERITY:
				raceInfo.setDexterity(bonus.getBonus());
				break;
			case CONSTITUTION:
				raceInfo.setConstitution(bonus.getBonus());
				break;
			case INTELLIGENCE:
				raceInfo.setIntellect(bonus.getBonus());
				break;
			case WISDOM:
				raceInfo.setWizdom(bonus.getBonus());
				break;
			case CHARISMA:
				raceInfo.setCharisma(bonus.getBonus());
				break;
			default:
				break;
			}
		}
		if (race.getParent() !=null) {
			for (AbilityBonus bonus : race.getParent().getAbilityValueBonuses()) {
				switch (bonus.getAbility()) {
				case STRENGTH:
					raceInfo.setStrength(bonus.getBonus());
					break;
				case DEXTERITY:
					raceInfo.setDexterity(bonus.getBonus());
					break;
				case CONSTITUTION:
					raceInfo.setConstitution(bonus.getBonus());
					break;
				case INTELLIGENCE:
					raceInfo.setIntellect(bonus.getBonus());
					break;
				case WISDOM:
					raceInfo.setWizdom(bonus.getBonus());
					break;
				case CHARISMA:
					raceInfo.setCharisma(bonus.getBonus());
					break;
				default:
					break;
				}
			}
		}
		heroModel.setRaceInfo(raceInfo);
	}

	public void removeRace(HeroModel heroModel) {
		heroModel.setRaceInfo(null);
	}

	@Transactional
	public List<RaceDto> getRaces() {
		return raceRepo.findAll(Sort.by("name")).stream().map(RaceDto::new).collect(Collectors.toList());
	}

	public String getRaceOrder() {
		return "[[ 1, 'asc' ]]";
	}

	@Transactional
	public RaceDto getRace(HeroModel heroModel) {
		if (heroModel.getRaceInfo() == null) {
			return null;
		}
		Race race = raceRepo.getOne(heroModel.getRaceInfo().getId());
		return new RaceDto(race);
	}
}