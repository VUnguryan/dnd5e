package com.dnd5e.wiki.model.gods;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {
	ABSOLUTE("абсолютное божество") {
		@Override
		public String getName(GodSex sex) {
			return "абсолютное";
		}
	},
	GREAT("великое божество") {
		@Override
		public String getName(GodSex sex) {
			switch (sex) {
			case MALE:
				return "великий";
			case FEMALE:
				return "великая";
			default:
				return "великое";
			}
		}
	},
	MIDDLE ("среднее божество"){
		@Override
		public String getName(GodSex sex) {
			switch (sex) {
			case MALE:
				return "средний";
			case FEMALE:
				return "средняя";
			default:
				return "среднее";
			}
		}
	},
	LESS ("младшее божество"){
		@Override
		public String getName(GodSex sex) {
			switch (sex) {
			case MALE:
				return "младший";
			case FEMALE:
				return "младшая";
			default:
				return "меньшее";
			}
		}
	},
	HALF("полу-бог") {
		@Override
		public String getName(GodSex sex) {
			return "полу - ";
		}
	},
	DEAD("мертвое божество") {
		@Override
		public String getName(GodSex sex) {
			switch (sex) {
			case MALE:
				return "мертвый";
			case FEMALE:
				return "мертвая";
			default:
				return "мертвое";
			}
		}
	};
	
	private String name;
	
	public abstract String getName(GodSex sex);
	
	public static Rank parse(String value) {
		return Arrays.stream(values()).filter(r -> r.getName().equals(value)).findFirst().orElseThrow(IllegalArgumentException::new);
	}
}