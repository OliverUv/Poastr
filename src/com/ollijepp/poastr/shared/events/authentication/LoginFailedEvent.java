package com.ollijepp.poastr.shared.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LoginFailedEvent extends GwtEvent<LoginFailedEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onLoginFailed(LoginFailedEvent event);
	}
	
	public static Type<LoginFailedEvent.Handler> TYPE = new Type<LoginFailedEvent.Handler>();
	
	@Override
	protected void dispatch(LoginFailedEvent.Handler handler) {
		handler.onLoginFailed(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginFailedEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}
