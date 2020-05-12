package com.dnd5e.wiki.controller.rest.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Search {
    private String value;
    private String regexp;
}
