package com.ollijepp.poastr.client.events.datastorage;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

/**
 * This event signals that an account has successfully been added
 * to the datastore. UI should respond to let user know this.
 * 
 * @author Oliver Uvman
 *
 */
public class AccountAddedEvent extends GwtEvent<AccountAddedEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onAccountAdded(AccountAddedEvent event);
	}
	
	private SocialService account;
	
	/**
	 * Returns the account that was successfully stored
	 * @return
	 */
	public SocialService getAccount() {
		return account;
	}

	public AccountAddedEvent(SocialService account) {
		super();
		this.account = account;
	}

	public static Type<AccountAddedEvent.Handler> TYPE = new Type<AccountAddedEvent.Handler>();
	
	@Override
	protected void dispatch(AccountAddedEvent.Handler handler) {
		handler.onAccountAdded(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AccountAddedEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}