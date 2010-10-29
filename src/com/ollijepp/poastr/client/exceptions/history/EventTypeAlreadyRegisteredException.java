package com.ollijepp.poastr.client.exceptions.history;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class EventTypeAlreadyRegisteredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6638482919194139361L;

	private GwtEvent.Type eventType;

	public EventTypeAlreadyRegisteredException(Type eventType) {
		super();
		this.eventType = eventType;
	}

	public GwtEvent.Type getEventType() {
		return eventType;
	}
	
}
