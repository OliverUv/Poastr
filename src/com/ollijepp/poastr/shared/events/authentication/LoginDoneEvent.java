package com.ollijepp.poastr.shared.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LoginDoneEvent extends GwtEvent<LoginDoneEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onLoginDone(LoginDoneEvent event);
	}

	public static Type<LoginDoneEvent.Handler> TYPE = new Type<LoginDoneEvent.Handler>();

	public LoginDoneEvent() {
	}

	@Override
	protected void dispatch(LoginDoneEvent.Handler handler) {
		handler.onLoginDone(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginDoneEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}
