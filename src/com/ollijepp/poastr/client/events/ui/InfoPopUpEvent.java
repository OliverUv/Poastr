package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class InfoPopUpEvent extends GwtEvent<InfoPopUpEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onInfoPopUp(InfoPopUpEvent event);
	}
	
	private String infoMessage; 
	
	public InfoPopUpEvent(String infoMessage) {
		super();
		this.infoMessage = infoMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public static Type<InfoPopUpEvent.Handler> TYPE = new Type<InfoPopUpEvent.Handler>();
	
	@Override
	protected void dispatch(InfoPopUpEvent.Handler handler) {
		handler.onInfoPopUp(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<InfoPopUpEvent.Handler> getAssociatedType() {
		return TYPE;
	}
}
