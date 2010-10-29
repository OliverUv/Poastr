package com.ollijepp.poastr.client.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DoLogoutEvent extends GwtEvent<DoLogoutEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onDoLogout(DoLogoutEvent event);
	}
	
	public static Type<DoLogoutEvent.Handler> TYPE = new Type<DoLogoutEvent.Handler>();
	
	@Override
	protected void dispatch(DoLogoutEvent.Handler handler) {
		handler.onDoLogout(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DoLogoutEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}
