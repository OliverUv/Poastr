package com.ollijepp.poastr.client;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Key;
import com.ollijepp.poastr.client.events.authentication.UserLoggedInEvent;
import com.ollijepp.poastr.client.events.authentication.UserLoggedOutEvent;
import com.ollijepp.poastr.client.events.datastorage.AccountAddedEvent;
import com.ollijepp.poastr.client.events.datastorage.AccountDeletedEvent;
import com.ollijepp.poastr.client.events.datastorage.AddAccountEvent;
import com.ollijepp.poastr.client.events.datastorage.DeleteAccountEvent;
import com.ollijepp.poastr.client.events.datastorage.AllAccountsEvent;
import com.ollijepp.poastr.client.events.datastorage.RequestAllAccountsEvent;
import com.ollijepp.poastr.client.events.datastorage.UpdateAccountEvent;
import com.ollijepp.poastr.client.infrastructure.GwtCallback;
import com.ollijepp.poastr.shared.datastorage.rpc.AddAccount;
import com.ollijepp.poastr.shared.datastorage.rpc.AddAccountResult;
import com.ollijepp.poastr.shared.datastorage.rpc.DeleteAccount;
import com.ollijepp.poastr.shared.datastorage.rpc.DeleteAccountResult;
import com.ollijepp.poastr.shared.datastorage.rpc.GetAccounts;
import com.ollijepp.poastr.shared.datastorage.rpc.GetAccountsResult;
import com.ollijepp.poastr.shared.socialService.AccountOwner;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;

/**
 * This class is intended to provide access to data sources 
 * for the rest of the application. Which data sources
 * should not matter for the rest of the application. In this case
 * we are using Google App Engine to store things.
 * 
 * When a user is logged in, the getUserInfo function will return
 * a string uniquely identifying the user.
 * 
 * @author Oliver Uvman
 *
 */
@Singleton
public class DataAdapter implements
UserLoggedInEvent.Handler,
UserLoggedOutEvent.Handler,
RequestAllAccountsEvent.Handler,
AddAccountEvent.Handler,
DeleteAccountEvent.Handler,
UpdateAccountEvent.Handler {
		
	private String userInfo;
	private String passPhrase = "";
	private boolean hasUserInfo = false;
	private final EventBus eventBus;
	private final DispatchAsync dispatch;
	
	@Inject
	DataAdapter(EventBus eventBus, DispatchAsync dispatch) {
		this.eventBus = eventBus;
		this.dispatch = dispatch;
		this.eventBus.addHandler(UserLoggedInEvent.TYPE, this);
		this.eventBus.addHandler(UserLoggedOutEvent.TYPE, this);
		this.eventBus.addHandler(RequestAllAccountsEvent.TYPE, this);
		this.eventBus.addHandler(AddAccountEvent.TYPE, this);
		this.eventBus.addHandler(DeleteAccountEvent.TYPE, this);
		this.eventBus.addHandler(UpdateAccountEvent.TYPE, this);
	}
	
	public boolean hasUserInfo(){
		return hasUserInfo;
	}
	
	public String getUserInfo(){
		return userInfo;
	}

	@Override
	public void onLoggedIn(UserLoggedInEvent event) {
		userInfo = event.getUserOpenId();
		hasUserInfo = true;
	}

	@Override
	public void onLoggedOut(UserLoggedOutEvent event) {
		hasUserInfo = false;
	}

	@Override
	public void onAllAccountsRequested(RequestAllAccountsEvent event) {
		dispatch.execute(
				new GetAccounts(new Key<AccountOwner>(AccountOwner.class, SocialServiceFactory.getHash(getUserInfo()))),
				new GwtCallback<GetAccountsResult>(dispatch, eventBus){
					@Override
					public void callback(GetAccountsResult result){
						eventBus.fireEvent(new AllAccountsEvent(result.getAccounts()));
					}
		});
	}

	public String getPassPhrase() {
		if (!passPhrase.equals("")){
			return passPhrase;
		} else {
			//TODO ask for a passphrase
			return "only for testing purposes";
		}
	}

	@Override
	public void onAddAccount(AddAccountEvent event) {
		dispatch.execute(
				new AddAccount(event.getSocialService()),
				new GwtCallback<AddAccountResult>(dispatch, eventBus){
			@Override
			public void callback(AddAccountResult result){
				eventBus.fireEvent(new AccountAddedEvent(result.getAccount()));
			}
		});
	}
	
	@Override
	public void onDeleteAccount(DeleteAccountEvent event) {
		dispatch.execute(
				new DeleteAccount(event.getSocialService()),
				new GwtCallback<DeleteAccountResult>(dispatch, eventBus){
			@Override
			public void callback(DeleteAccountResult result){
				eventBus.fireEvent(new AccountDeletedEvent(result.getAccount()));
			}
		});
	}
	
	@Override
	public void onUpdateAccount(UpdateAccountEvent event) {
		dispatch.execute(
				//At the moment, add account works like add or update
				new AddAccount(event.getSocialService()),
				new GwtCallback<AddAccountResult>(dispatch, eventBus){
			@Override
			public void callback(AddAccountResult result){
				eventBus.fireEvent(new AccountAddedEvent(result.getAccount()));
			}
		});
	}
}
