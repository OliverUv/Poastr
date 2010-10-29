package com.ollijepp.poastr.client.events.datastorage;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

public class AddAccountEvent extends GwtEvent<AddAccountEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		public void onAddAccount(AddAccountEvent event);
	}
	
	public static Type<AddAccountEvent.Handler> TYPE = new Type<AddAccountEvent.Handler>();

	private SocialService socialService;
	
	public AddAccountEvent(SocialService socialService)
	{
		this.socialService = socialService;
	}
	
	public SocialService getSocialService()
	{
		return this.socialService;
	}
	
	@Override
	protected void dispatch(AddAccountEvent.Handler handler) 
	{
		handler.onAddAccount(this);
	}

	@Override
	public Type<AddAccountEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}
}
