package com.ollijepp.poastr.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Our eventbus. We need a non-null constructor for
 * guice/gin to work easily.
 * 
 * @author Oliver Uvman
 *
 */
@Singleton
public class EventBus extends HandlerManager {
	
	@Inject
	public EventBus() {
		super(null);
	}

	@Override
	public <H extends EventHandler> HandlerRegistration addHandler(
			Type<H> type, H handler) {
		System.out.println();
		Log.info("EventBus: " + handler.toString() + " now handling " + type.toString());
		return super.addHandler(type, handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		Log.info("EventBus: " + event.toDebugString());
		super.fireEvent(event);
	}

	@Override
	public <H extends EventHandler> void removeHandler(Type<H> type, H handler) {
		Log.info("EventBus: " + handler.toString() + " no longer handling " + type.toString());
		super.removeHandler(type, handler);
	}
	
}