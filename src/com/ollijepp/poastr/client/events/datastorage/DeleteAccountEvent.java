package com.ollijepp.poastr.client.events.datastorage;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.shared.socialService.SocialService;

public class DeleteAccountEvent extends GwtEvent<DeleteAccountEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		public void onDeleteAccount(DeleteAccountEvent event);
	}
	
	public static Type<DeleteAccountEvent.Handler> TYPE = new Type<DeleteAccountEvent.Handler>();

	private SocialService socialService;
	
	public DeleteAccountEvent(SocialService socialService)
	{
		this.socialService = socialService;
	}
	
	public SocialService getSocialService()
	{
		return this.socialService;
	}
	
	@Override
	protected void dispatch(DeleteAccountEvent.Handler handler) 
	{
		handler.onDeleteAccount(this);
	}

	@Override
	public Type<DeleteAccountEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}
}
