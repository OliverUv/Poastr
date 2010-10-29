package com.ollijepp.poastr.client.events;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.ollijepp.poastr.client.events.authentication.DoAfterLoginEvent;
import com.ollijepp.poastr.shared.socialService.PoastBuilder;

public class PoastPoastsEvent extends GwtEvent<PoastPoastsEvent.Handler> {
	public interface Handler extends EventHandler {
		public void poastPoasts(PoastPoastsEvent event);
	}
	
	public static Type<PoastPoastsEvent.Handler> TYPE = new Type<PoastPoastsEvent.Handler>();
	private ArrayList<PoastBuilder> poasts;

	public PoastPoastsEvent(ArrayList<PoastBuilder> poasts) {
		this.poasts = poasts;
	}

	public ArrayList<PoastBuilder> getPoasts() {
		return poasts;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.poastPoasts(this);		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
