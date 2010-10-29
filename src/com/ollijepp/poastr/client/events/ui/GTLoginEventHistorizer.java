package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.ollijepp.poastr.client.history.AbstractEventHistorizer;

public class GTLoginEventHistorizer extends AbstractEventHistorizer {

	public GTLoginEventHistorizer() {
		super(GTLoginEvent.TYPE, "Login");
	}

	@Override
	protected GwtEvent decode(String eventRepresentation) {
		return new GTLoginEvent();
	}

	@Override
	protected String encode(GwtEvent<?> event) {
		return "";
	}

}
