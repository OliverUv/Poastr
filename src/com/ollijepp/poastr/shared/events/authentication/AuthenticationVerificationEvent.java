package com.ollijepp.poastr.shared.events.authentication;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AuthenticationVerificationEvent extends GwtEvent<AuthenticationVerificationEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onVerify(AuthenticationVerificationEvent event);
	}

	public static Type<AuthenticationVerificationEvent.Handler> TYPE = new Type<AuthenticationVerificationEvent.Handler>();
	private boolean verified;
	private String userOpenId;

	public AuthenticationVerificationEvent(boolean verified, String userOpenId) {
		this.verified = verified;
		this.userOpenId = userOpenId;
	}

	@Override
	protected void dispatch(AuthenticationVerificationEvent.Handler handler) {
		handler.onVerify(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AuthenticationVerificationEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	public boolean isVerified() {
		return verified;
	}

	public String getUserOpenId() {
		return userOpenId;
	}
}
