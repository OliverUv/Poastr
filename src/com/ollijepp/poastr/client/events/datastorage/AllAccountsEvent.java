package com.ollijepp.poastr.client.events.datastorage;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

/**
 * This event contains new information for all accounts. Usually fired
 * by the DataAdapter in response to a {@link RequestAllAccountsEvent}.
 * 
 * @author Oliver Uvman
 *
 */
public class AllAccountsEvent extends GwtEvent<AllAccountsEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onAllAccountsReceived(AllAccountsEvent event);
	}
	
	private List<SocialService> accounts;
	
	public List<SocialService> getAllAccounts() {
		return accounts;
	}

	public AllAccountsEvent(List<SocialService> accounts) {
		super();
		this.accounts = accounts;
	}

	public static Type<AllAccountsEvent.Handler> TYPE = new Type<AllAccountsEvent.Handler>();
	
	@Override
	protected void dispatch(AllAccountsEvent.Handler handler) {
		handler.onAllAccountsReceived(this);
	}
	
	

	@Override
	public String toDebugString() {
		String contents = "";
		for(SocialService s : accounts){
			contents += s.getUserNameEncrypted() + "\n ";
		}
		return super.toDebugString() + contents;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AllAccountsEvent.Handler> getAssociatedType() {
		return TYPE;
	}

}