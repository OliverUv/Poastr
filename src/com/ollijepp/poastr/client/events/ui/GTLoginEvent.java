package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class GTLoginEvent extends GwtEvent<GTLoginEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		void onGTLogin(GTLoginEvent event);
	}

	public static Type<GTLoginEvent.Handler> TYPE = new Type<GTLoginEvent.Handler>();

	@Override
	protected void dispatch(GTLoginEvent.Handler handler) 
	{
		handler.onGTLogin(this);
	}

	@Override
	public Type<GTLoginEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}

}
