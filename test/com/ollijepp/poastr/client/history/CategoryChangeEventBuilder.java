package com.ollijepp.poastr.client.history;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public 	class CategoryChangeEventBuilder extends AbstractEventHistorizer{

	public CategoryChangeEventBuilder(Type eventType, String uniqueIdentifier) {
		super(eventType, uniqueIdentifier);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected GwtEvent decode(String eventRepresentation) {
		ArrayList<String> l = new ArrayList<String>();
		String[] categories = eventRepresentation.split(",");
		for (String category : categories){
			l.add(category);
		}
		return new CategoryChangeEvent(l);
	}	

	@Override
	protected String encode(GwtEvent<?> event) {
		List<String> l = ((CategoryChangeEvent)event).getCategories();
		String representation = "";
		for (String category : l){
			representation += category + ',';
		}
		representation = representation.substring(0,
				representation.length()-1); //remove last comma
		return representation;
	}
}
