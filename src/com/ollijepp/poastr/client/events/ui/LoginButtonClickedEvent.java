package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LoginButtonClickedEvent extends GwtEvent<LoginButtonClickedEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onLoginButtonClicked(LoginButtonClickedEvent event);
	}
	
	public static Type<LoginButtonClickedEvent.Handler> TYPE = new Type<LoginButtonClickedEvent.Handler>();
	
	@Override
	protected void dispatch(LoginButtonClickedEvent.Handler handler) {
		handler.onLoginButtonClicked(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginButtonClickedEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}