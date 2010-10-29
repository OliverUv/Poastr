package com.ollijepp.poastr.client.history;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.events.ui.GTCreatePostEvent;
import com.ollijepp.poastr.client.events.ui.GTLoginEvent;

@Singleton
public class PoastrHistoryHandler extends HistoryHandler implements
	GTCreatePostEvent.Handler,
	GTLoginEvent.Handler {
	
	@Inject
	public PoastrHistoryHandler(EventBus eventBus) {
		super(eventBus);
	}

	@Override
	public void onGTCreatePost(GTCreatePostEvent event) {
		onHistoricalEvent(event);
	}

	@Override
	public void onGTLogin(GTLoginEvent event) {
		onHistoricalEvent(event);
	}
}