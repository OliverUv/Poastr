package com.ollijepp.poastr.client.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * This event instructs the AuthManager to log in.
 * 
 * @author Oliver Uvman
 *
 */
public class DoLoginEvent extends GwtEvent<DoLoginEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onDoLogin(DoLoginEvent event);
	}
	
	public static Type<DoLoginEvent.Handler> TYPE = new Type<DoLoginEvent.Handler>();
	
	@Override
	protected void dispatch(DoLoginEvent.Handler handler) {
		handler.onDoLogin(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DoLoginEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}