package com.ollijepp.poastr.shared.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LogoutDoneEvent extends GwtEvent<LogoutDoneEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onLogoutDone(LogoutDoneEvent event);
	}

	public static Type<LogoutDoneEvent.Handler> TYPE = new Type<LogoutDoneEvent.Handler>();

	public LogoutDoneEvent() {
	}

	@Override
	protected void dispatch(LogoutDoneEvent.Handler handler) {
		handler.onLogoutDone(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LogoutDoneEvent.Handler> getAssociatedType() {
		return TYPE;
	}
}
