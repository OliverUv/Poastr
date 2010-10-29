package com.ollijepp.poastr.client.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.ollijepp.poastr.shared.events.authentication.LoginDoneEvent;

public class UserLoggedOutEvent extends GwtEvent<UserLoggedOutEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onLoggedOut(UserLoggedOutEvent event);
	}

	public static Type<UserLoggedOutEvent.Handler> TYPE = new Type<UserLoggedOutEvent.Handler>();

	public UserLoggedOutEvent() {
	}

	@Override
	protected void dispatch(UserLoggedOutEvent.Handler handler) {
		handler.onLoggedOut(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserLoggedOutEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}
