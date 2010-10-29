package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AddAccountPopUpEvent extends GwtEvent<AddAccountPopUpEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		void onAddAccountPopUp(AddAccountPopUpEvent event);
	}
	
	public static Type<AddAccountPopUpEvent.Handler> TYPE = new Type<AddAccountPopUpEvent.Handler>();

	@Override
	protected void dispatch(AddAccountPopUpEvent.Handler handler) 
	{
		handler.onAddAccountPopUp(this);
	}

	@Override
	public Type<AddAccountPopUpEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}
	
}
