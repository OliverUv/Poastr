package com.ollijepp.poastr.client.exceptions.history;

public class EventNotHistorizableException extends Exception {
	String eventType;
	public EventNotHistorizableException(String eventType) {
		super();
		this.eventType = eventType;
	}
	public String getEventType(){
		return eventType;
	}
}
