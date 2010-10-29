package com.ollijepp.poastr.client.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * This event instructs the AuthManager to fire a
 * particular event after it has successfully logged in.
 * 
 * @author Oliver Uvman
 *
 */
public class DoAfterLoginEvent extends GwtEvent<DoAfterLoginEvent.Handler> {
	public interface Handler extends EventHandler {
		public void afterLoginDone(DoAfterLoginEvent event);
	}
	
	public static Type<DoAfterLoginEvent.Handler> TYPE = new Type<DoAfterLoginEvent.Handler>();
	
	private final GwtEvent<?> afterLoggedIn;
	
	/**
	 * This event tells the AuthManager to log in, and optionally fire another
	 * event after successful login.
	 * 
	 * @param doAfterLoggedIn an event to fire after successful login, or null
	 */
	public DoAfterLoginEvent(GwtEvent<?> doAfterLoggedIn) {
		this.afterLoggedIn = doAfterLoggedIn;		
	}
	
	public GwtEvent<?> eventToDoAfter() {
		return afterLoggedIn;
	}

	@Override
	protected void dispatch(DoAfterLoginEvent.Handler handler) {
		handler.afterLoginDone(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DoAfterLoginEvent.Handler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	public String toDebugString(){
		return super.toDebugString() + " " + afterLoggedIn.toDebugString();
	}

}