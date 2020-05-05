package com.dnd5e.wiki.model.gods;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Rank {
	ABSOLUTE() {
		@Override
		public String getName(GodSex sex) {
			return "абсолютное";
		}
	},
	GREAT() {
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
	MIDDLE {
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
	LESS {
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
	HALF {
		@Override
		public String getName(GodSex sex) {
			return "полу - ";
		}
	},
	DEAD {
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
	public abstract String getName(GodSex sex);
}