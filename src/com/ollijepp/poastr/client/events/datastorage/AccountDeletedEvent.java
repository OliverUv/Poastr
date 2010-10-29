package com.ollijepp.poastr.client.events.datastorage;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

/**
 * This event signals that an account has successfully been deleted
 * from the datastore. UI should respond to let user know this.
 * 
 * @author Oliver Uvman
 *
 */
public class AccountDeletedEvent extends GwtEvent<AccountDeletedEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onAccountDeleted(AccountDeletedEvent event);
	}
	
	private SocialService account;
	
	/**
	 * Returns the account that was successfully deleted
	 * @return
	 */
	public SocialService getAccount() {
		return account;
	}

	public AccountDeletedEvent(SocialService account) {
		super();
		this.account = account;
	}

	public static Type<AccountDeletedEvent.Handler> TYPE = new Type<AccountDeletedEvent.Handler>();
	
	@Override
	protected void dispatch(AccountDeletedEvent.Handler handler) {
		handler.onAccountDeleted(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AccountDeletedEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}