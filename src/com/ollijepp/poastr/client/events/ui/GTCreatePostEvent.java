package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class GTCreatePostEvent extends GwtEvent<GTCreatePostEvent.Handler>
{
	public interface Handler extends EventHandler
	{
		void onGTCreatePost(GTCreatePostEvent event);
	}
	
	public static Type<GTCreatePostEvent.Handler> TYPE = new Type<GTCreatePostEvent.Handler>();
	
	@Override
	protected void dispatch(GTCreatePostEvent.Handler handler) 
	{
		handler.onGTCreatePost(this);
	}

	@Override
	public Type<GTCreatePostEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}

}
