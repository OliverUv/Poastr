package com.ollijepp.poastr.client.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * This event instructs the AuthManager to check whether the user
 * is logged in or not. The AuthManager checks this, and then fires
 * either a UserLoggedInEvent or a UserLoggedOutEvent.
 * 
 * @author Oliver Uvman
 *
 */
public class CheckSessionEvent extends GwtEvent<CheckSessionEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onCheckSession(CheckSessionEvent event);
	}
	
	public static Type<CheckSessionEvent.Handler> TYPE = new Type<CheckSessionEvent.Handler>();
	
	@Override
	protected void dispatch(CheckSessionEvent.Handler handler) {
		handler.onCheckSession(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CheckSessionEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}