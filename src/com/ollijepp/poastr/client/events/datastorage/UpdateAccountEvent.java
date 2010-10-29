package com.ollijepp.poastr.client.events.datastorage;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

public class UpdateAccountEvent extends GwtEvent<UpdateAccountEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		public void onUpdateAccount(UpdateAccountEvent event);
	}
	
	public static Type<UpdateAccountEvent.Handler> TYPE = new Type<UpdateAccountEvent.Handler>();

	private SocialService socialService;
	
	public UpdateAccountEvent(SocialService socialService)
	{
		this.socialService = socialService;
	}
	
	public SocialService getSocialService()
	{
		return this.socialService;
	}
	
	@Override
	protected void dispatch(UpdateAccountEvent.Handler handler) 
	{
		handler.onUpdateAccount(this);
	}

	@Override
	public Type<UpdateAccountEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}
}
