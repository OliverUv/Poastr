package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AddAccountButtonClickedEvent extends GwtEvent<AddAccountButtonClickedEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		public void onAddAccountButtonClicked(AddAccountButtonClickedEvent event);
	}
	
	public static Type<AddAccountButtonClickedEvent.Handler> TYPE = new Type<AddAccountButtonClickedEvent.Handler>();

	@Override
	protected void dispatch(AddAccountButtonClickedEvent.Handler handler) 
	{
		handler.onAddAccountButtonClicked(this);
	}

	@Override
	public Type<AddAccountButtonClickedEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}
	
	
}
