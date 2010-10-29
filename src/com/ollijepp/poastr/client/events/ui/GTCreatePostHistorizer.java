package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.ollijepp.poastr.client.history.AbstractEventHistorizer;

public class GTCreatePostHistorizer extends AbstractEventHistorizer {

	public GTCreatePostHistorizer() {
		super(GTCreatePostEvent.TYPE, "CreatePost");
	}

	@Override
	protected GwtEvent decode(String eventRepresentation) {
		return new GTCreatePostEvent();
	}

	@Override
	protected String encode(GwtEvent<?> event) {
		return "";
	}

}
