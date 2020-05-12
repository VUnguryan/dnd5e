package com.dnd5e.wiki.controller.rest.paging;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> {
    private List<T> data;
    private int recordsFiltered;
    private int recordsTotal;
    private int draw;
    public PageResult(List<T> data) {
        this.data = data;
    }
}