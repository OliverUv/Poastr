package com.ollijepp.poastr.client.ui.createPost;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.Poastr;
import com.ollijepp.poastr.client.crypto.ClipperzCrypto;
import com.ollijepp.poastr.client.events.ProcessPoastsEvent;
import com.ollijepp.poastr.client.events.datastorage.AccountAddedEvent;
import com.ollijepp.poastr.client.events.datastorage.AccountDeletedEvent;
import com.ollijepp.poastr.client.events.datastorage.AllAccountsEvent;
import com.ollijepp.poastr.client.events.datastorage.RequestAllAccountsEvent;
import com.ollijepp.poastr.client.events.ui.AddAccountButtonClickedEvent;
import com.ollijepp.poastr.client.events.ui.AddAccountPopUpEvent;
import com.ollijepp.poastr.client.events.ui.GTCreatePostEvent;
import com.ollijepp.poastr.client.events.ui.InfoPopUpEvent;
import com.ollijepp.poastr.client.events.ui.PoastButtonClickedEvent;
import com.ollijepp.poastr.shared.socialService.Facebook;
import com.ollijepp.poastr.shared.socialService.FacebookProvider;
import com.ollijepp.poastr.shared.socialService.PoastBuilder;
import com.ollijepp.poastr.shared.socialService.SocialService;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;
import com.ollijepp.poastr.shared.socialService.Twitter;
import com.ollijepp.poastr.shared.socialService.TwitterProvider;
import com.ollijepp.poastr.client.ui.Presenter;

@Singleton
public class CreatePostPresenter implements
Presenter,
GTCreatePostEvent.Handler, 
PoastButtonClickedEvent.Handler,
AddAccountButtonClickedEvent.Handler,
AccountAddedEvent.Handler,
AllAccountsEvent.Handler,
AccountDeletedEvent.Handler
{
	public interface Display
	{
		HasValue<String> getPostText();
		Widget asWidget();
		
		void setShowUser(String userInfo);
		void setSocialServices(ArrayList<AccountWidgetPresenter> socialServiceList);
		HandlerRegistration addPoastButtonClickedEventHandler(PoastButtonClickedEvent.Handler handler);
		HandlerRegistration addAddAccountButtonClickedEventHandler(AddAccountButtonClickedEvent.Handler handler);
	}
	
	private final EventBus eventBus;
	private final Display display;
	private final HasWidgets container;
	private final DataAdapter dataAdapter;
	private final SocialServiceFactory ssFact;
	private ArrayList<AccountWidgetPresenter> accountWidgetList;
	
	@Inject
	public CreatePostPresenter(
			EventBus eventBus,
			Display display,
			DataAdapter dataAdapter,
			SocialServiceFactory socialServiceFactory)
	{
		this.eventBus = eventBus;
		this.display = display;
		this.dataAdapter = dataAdapter;
		this.ssFact = socialServiceFactory;
		this.accountWidgetList = new ArrayList<AccountWidgetPresenter>();
		this.container = Poastr.getAppSpace();

		this.display.addPoastButtonClickedEventHandler(this);
		this.display.addAddAccountButtonClickedEventHandler(this);
				
		this.eventBus.addHandler(GTCreatePostEvent.TYPE, this);
		this.eventBus.addHandler(PoastButtonClickedEvent.TYPE, this);
		this.eventBus.addHandler(AccountAddedEvent.TYPE, this);
		this.eventBus.addHandler(AllAccountsEvent.TYPE, this);
		this.eventBus.addHandler(AccountDeletedEvent.TYPE, this);
	}
	
	@Override
	public void go(HasWidgets container)
	{
		if(dataAdapter.hasUserInfo()){
			display.setShowUser("OpenID: " + dataAdapter.getUserInfo());
		}
		else
		{
			display.setShowUser("No user info");
		}
		eventBus.fireEvent(new RequestAllAccountsEvent());
		//TODO: Might want to fire this at some earlier points, eg on UserLoggedInEvent
		/*ArrayList<SocialService> socialServiceList = new ArrayList<SocialService>();
		String user = this.dataAdapter.getUserInfo();
		//TODO: Fix user login when user is not logged in
		if (user == null || user.equals("")){ user = "nonopenidstrang";}
		*/
		
		container.clear();
		container.add(display.asWidget());
	}

	@Override
	public void onGTCreatePost(GTCreatePostEvent event) {
		go(container);
	}

	@Override
	public void onPoastButtonClicked(PoastButtonClickedEvent event) 
	{		
		ArrayList<PoastBuilder> pbl = new ArrayList<PoastBuilder>();
		//TEST of twitter functionality
		/*
		Twitter twt = (Twitter) ssFact.buildSocialService(
				new TwitterProvider(),
				dataAdapter.getUserInfo(),
				"Poastr",
				"password");
		pbl.add(twt.buildPoast("First Poastr post???"));
		
		*/
		
		// The real deal
		for (AccountWidgetPresenter account : event.getCheckedAccountList())
		{
			pbl.add(account.getService().buildPoast(event.getPoast()));
		}
		this.eventBus.fireEvent(new ProcessPoastsEvent(pbl));
		this.eventBus.fireEvent(new InfoPopUpEvent("Poasting hasn't been <br>implemented yet"));
	}

	@Override
	public void onAddAccountButtonClicked(AddAccountButtonClickedEvent event) 
	{
		this.eventBus.fireEvent(new AddAccountPopUpEvent());
	}

	@Override
	public void onAccountAdded(AccountAddedEvent event) {
		SocialService service = event.getAccount();
		AccountWidgetPresenter accountWidget = new AccountWidgetPresenter(ssFact.getClearTextName(service), service, eventBus); 
		accountWidgetList.add(accountWidget);
		display.setSocialServices(accountWidgetList);
	}

	@Override
	public void onAllAccountsReceived(AllAccountsEvent event) {
		accountWidgetList.clear();
		
		for (SocialService service : event.getAllAccounts())
		{
			AccountWidgetPresenter accountWidget = new AccountWidgetPresenter(ssFact.getClearTextName(service), service, eventBus);
			accountWidgetList.add(accountWidget);
		}
		
		display.setSocialServices(accountWidgetList);
	}

	@Override
	public void onAccountDeleted(AccountDeletedEvent event) 
	{
		/**
		 * accountWidgetList.remove(event.getAccount()) doesn't work because it
		 * is not the same object, but a copy created by serialization, but the
		 * following works even with duplicate user names, thanks to randomness
		 * of encryption. :)
		 */
		int stop = accountWidgetList.size();
		for (int i=0; i<stop; i++)
		{
			if (accountWidgetList.get(i).getService().getUserNameEncrypted().equals(event.getAccount().getUserNameEncrypted()))
			{
				accountWidgetList.remove(i);
				break;
			}
		}
		display.setSocialServices(accountWidgetList);
	}
}		