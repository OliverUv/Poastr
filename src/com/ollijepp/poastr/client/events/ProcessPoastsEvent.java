package com.ollijepp.poastr.client.events;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.ollijepp.poastr.client.events.authentication.DoAfterLoginEvent;
import com.ollijepp.poastr.shared.socialService.PoastBuilder;

public class ProcessPoastsEvent extends GwtEvent<ProcessPoastsEvent.Handler> {
	public interface Handler extends EventHandler {
		public void processPoasts(ProcessPoastsEvent event);
	}
	
	public static Type<ProcessPoastsEvent.Handler> TYPE = new Type<ProcessPoastsEvent.Handler>();
	private ArrayList<PoastBuilder> poasts;

	public ProcessPoastsEvent(ArrayList<PoastBuilder> poasts) {
		this.poasts = poasts;
	}

	public ArrayList<PoastBuilder> getPoasts() {
		return poasts;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.processPoasts(this);		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
