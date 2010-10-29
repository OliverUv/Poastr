package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DeleteAccountClickedEvent extends GwtEvent<DeleteAccountClickedEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		public void onDeleteAccountClicked(DeleteAccountClickedEvent event);
	}
	
	public static Type<DeleteAccountClickedEvent.Handler> TYPE = new Type<DeleteAccountClickedEvent.Handler>();

	@Override
	protected void dispatch(DeleteAccountClickedEvent.Handler handler) 
	{
		handler.onDeleteAccountClicked(this);
	}

	@Override
	public Type<DeleteAccountClickedEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}
}
