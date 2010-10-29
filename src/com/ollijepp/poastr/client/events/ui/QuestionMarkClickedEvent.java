package com.ollijepp.poastr.client.events.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class QuestionMarkClickedEvent extends GwtEvent<QuestionMarkClickedEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onQuestionMarkClicked(QuestionMarkClickedEvent event);
	}
	
	private String infoMessage;

	public String getInfoMessage() {
		return infoMessage;
	}

	public QuestionMarkClickedEvent(String infoMessage) {
		super();
		this.infoMessage = infoMessage;
	}

	public static Type<QuestionMarkClickedEvent.Handler> TYPE = new Type<QuestionMarkClickedEvent.Handler>();
	
	@Override
	protected void dispatch(QuestionMarkClickedEvent.Handler handler) {
		handler.onQuestionMarkClicked(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<QuestionMarkClickedEvent.Handler> getAssociatedType() {
		return TYPE;
	}
}
