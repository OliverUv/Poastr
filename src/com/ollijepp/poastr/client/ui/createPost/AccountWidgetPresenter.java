package com.ollijepp.poastr.client.ui.createPost;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.Poastr;
import com.ollijepp.poastr.client.ui.Presenter;
import com.ollijepp.poastr.client.events.datastorage.DeleteAccountEvent;
import com.ollijepp.poastr.client.events.ui.DeleteAccountClickedEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

public class AccountWidgetPresenter extends Composite implements Presenter, 
DeleteAccountClickedEvent.Handler
{

	public interface Display
	{
		Widget asWidget();
		
		boolean isChecked();
		HandlerRegistration addDeleteAccountClickedEventHandler(DeleteAccountClickedEvent.Handler handler);
	}
	
	private final EventBus eventBus;
	private final Display display;
	private final SocialService service;	
	
	public AccountWidgetPresenter(String username, SocialService service, EventBus eventBus)
	{
		this.eventBus = eventBus;
		this.service = service;
		this.display = new AccountWidgetView(username, service.getLogoPath());
		display.addDeleteAccountClickedEventHandler(this);
		
		initWidget(display.asWidget());
	}

	public boolean isChecked()
	{
		return display.isChecked();
	}
	
	public SocialService getService()
	{
		return this.service;
	}
	
	@Override
	public void onDeleteAccountClicked(DeleteAccountClickedEvent event) 
	{
		this.eventBus.fireEvent(new DeleteAccountEvent(this.service));
	}

	@Override
	public void go(HasWidgets container) 
	{
		// We don't need to do nothing here since the part of the 
		// application that instantiated this widget takes care of 
		// showing it
	}	
}
