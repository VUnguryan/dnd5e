package com.dnd5e.wiki.controller.rest.paging;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchPanesOutput<T> extends DataTablesOutput<T> {
	@JsonView(View.class)
    private SearchPanes searchPanes;
    
    public SearchPanesOutput(DataTablesOutput<T> output) {
    	setData(output.getData());
    	setDraw(output.getDraw());
    	setError(output.getError());
    	setRecordsFiltered(output.getRecordsFiltered());
    	setRecordsTotal(output.getRecordsTotal());
    }
    
    public SearchPanesOutput(List<T> data) {
    	setData(data);
    }
}