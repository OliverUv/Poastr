package com.ollijepp.poastr.client.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.ollijepp.poastr.shared.events.authentication.LoginDoneEvent;

public class UserLoggedInEvent extends GwtEvent<UserLoggedInEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onLoggedIn(UserLoggedInEvent event);
	}

	private String userOpenId;
	public static Type<UserLoggedInEvent.Handler> TYPE = new Type<UserLoggedInEvent.Handler>();

	public UserLoggedInEvent(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	@Override
	protected void dispatch(UserLoggedInEvent.Handler handler) {
		handler.onLoggedIn(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserLoggedInEvent.Handler> getAssociatedType() {
		return TYPE;
	}
	
	public String getUserOpenId() {
		return userOpenId;
	}

}
