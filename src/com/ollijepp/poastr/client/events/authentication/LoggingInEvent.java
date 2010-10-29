package com.ollijepp.poastr.client.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LoggingInEvent extends GwtEvent<LoggingInEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onLoggingIn(LoggingInEvent event);
	}
	
	public static Type<LoggingInEvent.Handler> TYPE = new Type<LoggingInEvent.Handler>();
	
	@Override
	protected void dispatch(LoggingInEvent.Handler handler) {
		handler.onLoggingIn(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoggingInEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}
