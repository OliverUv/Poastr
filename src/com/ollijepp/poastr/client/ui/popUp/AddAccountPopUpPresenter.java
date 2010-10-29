package com.ollijepp.poastr.client.ui.popUp;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.crypto.ClipperzCrypto;
import com.ollijepp.poastr.client.events.datastorage.AddAccountEvent;
import com.ollijepp.poastr.client.events.ui.AddAccountPopUpEvent;
import com.ollijepp.poastr.client.events.ui.PoastButtonClickedEvent;
import com.ollijepp.poastr.client.events.ui.PopUpAddAccountButtonClickedEvent;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.Poastr;
import com.ollijepp.poastr.client.ui.Presenter;
import com.ollijepp.poastr.shared.socialService.FacebookProvider;
import com.ollijepp.poastr.shared.socialService.ServiceProvider;
import com.ollijepp.poastr.shared.socialService.SocialService;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;
import com.ollijepp.poastr.shared.socialService.TwitterProvider;

/**
 * TODO: When selecting an account type, put a square around it or something instead
 * of changing the background colour.
 * TODO: Make the ui not require a password to be entered if serviceProvider.getAuthMethod
 * == AuthMethod.OAuth
 * 
 * @author Jesper Axelsson
 *
 */

@Singleton
public class AddAccountPopUpPresenter implements Presenter, AddAccountPopUpEvent.Handler, 
PopUpAddAccountButtonClickedEvent.Handler
{
	public interface Display
	{
		void show();
		Widget asWidget();
		
		void setSocialServiceLogos(List<ServiceProvider> serviceProviderList);
		HandlerRegistration addPopUpAddAccountButtonClickedEventHandler(PopUpAddAccountButtonClickedEvent.Handler handler);
	}
	
	private final HandlerManager eventBus;
	private final Display display;
	private final HasWidgets container;
	private final DataAdapter dataAdapter;
	private final SocialServiceFactory ssFact; 
	
	@Inject
	public AddAccountPopUpPresenter(EventBus eventBus, Display display, DataAdapter dataAdapter, SocialServiceFactory ssFact)
	{
		this.eventBus = eventBus;
		this.display = display;
		this.dataAdapter = dataAdapter;
		this.ssFact = ssFact;
		this.container = Poastr.getAppSpace();
		display.setSocialServiceLogos(ssFact.getProviders());
		
		this.display.addPopUpAddAccountButtonClickedEventHandler(this);
		
		this.eventBus.addHandler(AddAccountPopUpEvent.TYPE, this);
	}

	@Override
	public void onAddAccountPopUp(AddAccountPopUpEvent event) 
	{
		go(this.container);
	}
	
	@Override
	public void go(HasWidgets container) 
	{
		display.show();
	}

	@Override
	public void onPopUpAddAccountButtonClicked(
			PopUpAddAccountButtonClickedEvent event) 
	{	
		SocialService ss = ssFact.buildSocialService(
				event.getServiceProvider(),
				dataAdapter.getUserInfo(),
				event.getUsername(),
				event.getPassword());
		
		// Then fire away!
		this.eventBus.fireEvent(new AddAccountEvent(ss));
	}
}
