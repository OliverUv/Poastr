package com.ollijepp.poastr.client.history;

import com.google.gwt.event.shared.HandlerManager;
import com.ollijepp.poastr.client.EventBus;

public class CategoryHistoryHandler extends HistoryHandler implements CategoryChangeHandler {

	public CategoryHistoryHandler(EventBus eventBus) {
		super(eventBus);
	}

	@Override
	public void onCategoryChange(CategoryChangeEvent event) {
		onHistoricalEvent(event);
	}

}
