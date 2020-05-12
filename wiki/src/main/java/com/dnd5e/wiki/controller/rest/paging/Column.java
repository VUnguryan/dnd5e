package com.dnd5e.wiki.controller.rest.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Column {
    private String data;
    private String name;
    private Boolean searchable;
    private Boolean orderable;
    private Search search;
}