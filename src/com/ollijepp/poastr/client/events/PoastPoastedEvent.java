package com.ollijepp.poastr.client.events;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.ollijepp.poastr.client.events.authentication.DoAfterLoginEvent;
import com.ollijepp.poastr.shared.socialService.PoastBuilder;

public class PoastPoastedEvent extends GwtEvent<PoastPoastedEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onPoastPoasted(PoastPoastedEvent event);
	}
	
	public static Type<PoastPoastedEvent.Handler> TYPE = new Type<PoastPoastedEvent.Handler>();

	@Override
	protected void dispatch(Handler handler) {
		handler.onPoastPoasted(this);		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
