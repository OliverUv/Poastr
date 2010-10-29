package com.ollijepp.poastr.client.events.datastorage;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * This events asks the DataAdapter to send new information for all accounts.
 * Usually only fired at application startup.
 * 
 * @author Oliver Uvman
 *
 */
public class RequestAllAccountsEvent extends GwtEvent<RequestAllAccountsEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onAllAccountsRequested(RequestAllAccountsEvent event);
	}
	
	public static Type<RequestAllAccountsEvent.Handler> TYPE = new Type<RequestAllAccountsEvent.Handler>();
	
	@Override
	protected void dispatch(RequestAllAccountsEvent.Handler handler) {
		handler.onAllAccountsRequested(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RequestAllAccountsEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}