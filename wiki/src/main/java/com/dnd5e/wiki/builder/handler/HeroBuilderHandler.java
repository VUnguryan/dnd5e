package com.dnd5e.wiki.builder.handler;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;

import com.dnd5e.wiki.builder.model.HeroInfo;
import com.dnd5e.wiki.builder.model.HeroModel;

@Component
public class HeroBuilderHandler {
	public HeroModel init() {
		return new HeroModel();
	}

	public void addHeroInfo(HeroModel heroModel, HeroInfo heroInfo) {
		heroModel.setHeroInfo(heroInfo);
	}

	public String validateHeroInfo(HeroInfo personalInfo, MessageContext context) {
		String transitionValue = "success";
		if (personalInfo.getName().length() < 2) {
			context.addMessage(new MessageBuilder(). //
					error() //
					.source("name") //
					.defaultText("Имя должно содержать минимум два символа") //
					.build());
			transitionValue = "failure";
		}
		return transitionValue;
	}
}