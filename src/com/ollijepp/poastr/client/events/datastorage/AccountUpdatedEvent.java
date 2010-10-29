package com.ollijepp.poastr.client.events.datastorage;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

/**
 * This event signals that an account has successfully been updated
 * to the datastore. UI should respond to let user know this.
 * 
 * @author Oliver Uvman
 *
 */
public class AccountUpdatedEvent extends GwtEvent<AccountUpdatedEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onAccountUpdated(AccountUpdatedEvent event);
	}
	
	private SocialService account;
	
	/**
	 * Returns the account that was successfully stored
	 * @return
	 */
	public SocialService getAccount() {
		return account;
	}

	public AccountUpdatedEvent(SocialService account) {
		super();
		this.account = account;
	}

	public static Type<AccountUpdatedEvent.Handler> TYPE = new Type<AccountUpdatedEvent.Handler>();
	
	@Override
	protected void dispatch(AccountUpdatedEvent.Handler handler) {
		handler.onAccountUpdated(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AccountUpdatedEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}