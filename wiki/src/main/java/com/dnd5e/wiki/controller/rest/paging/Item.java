package com.dnd5e.wiki.controller.rest.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item <T> {
	T label;
    Long total;
    String value;
    Long count;
}