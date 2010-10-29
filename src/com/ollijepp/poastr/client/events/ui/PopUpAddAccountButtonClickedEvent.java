package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;
import com.ollijepp.poastr.shared.socialService.ServiceProvider;

public class PopUpAddAccountButtonClickedEvent extends GwtEvent<PopUpAddAccountButtonClickedEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		public void onPopUpAddAccountButtonClicked(PopUpAddAccountButtonClickedEvent event);
	}
	
	public static Type<PopUpAddAccountButtonClickedEvent.Handler> TYPE = new Type<PopUpAddAccountButtonClickedEvent.Handler>();

	private String username;
	private String password;
	private ServiceProvider serviceProvider;
	
	public PopUpAddAccountButtonClickedEvent(String username, String password, ServiceProvider serviceProvider)
	{
		this.username = username;
		this.password = password;
		this.serviceProvider = serviceProvider;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public ServiceProvider getServiceProvider()
	{
		return this.serviceProvider;
	}
	
	@Override
	protected void dispatch(PopUpAddAccountButtonClickedEvent.Handler handler) 
	{
		handler.onPopUpAddAccountButtonClicked(this);
	}

	@Override
	public Type<PopUpAddAccountButtonClickedEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}
}
