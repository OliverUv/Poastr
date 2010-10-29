package com.ollijepp.poastr.client;

//import org.mortbay.log.Log;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.events.*;
import com.ollijepp.poastr.client.events.ui.*;
import com.ollijepp.poastr.client.events.authentication.*;
import com.ollijepp.poastr.shared.events.*;
import com.ollijepp.poastr.shared.events.authentication.*;


@Singleton
public class EventLogger implements 
//authentication events
DoAfterLoginEvent.Handler,
DoLoginEvent.Handler,
DoLogoutEvent.Handler,
LoggingInEvent.Handler,
UserLoggedInEvent.Handler,
UserLoggedOutEvent.Handler,
//ui events
GTCreatePostEvent.Handler,
GTLoginEvent.Handler,
LoginButtonClickedEvent.Handler,
//shared events
ServerStatusEvent.Handler,
//shared auth events
AuthenticationVerificationEvent.Handler,
LoginDoneEvent.Handler,
LoginFailedEvent.Handler,
LogoutDoneEvent.Handler
{
	
	private final EventBus eventBus;
	
	@Inject
	public EventLogger(EventBus eventBus) {
		super();
		this.eventBus = eventBus;
		this.eventBus.addHandler(DoAfterLoginEvent.TYPE, this);
		this.eventBus.addHandler(DoLoginEvent.TYPE, this);
		this.eventBus.addHandler(DoLogoutEvent.TYPE, this);
		this.eventBus.addHandler(LoggingInEvent.TYPE, this);
		this.eventBus.addHandler(UserLoggedInEvent.TYPE, this);
		this.eventBus.addHandler(UserLoggedOutEvent.TYPE, this);
		this.eventBus.addHandler(GTCreatePostEvent.TYPE, this);
		this.eventBus.addHandler(GTLoginEvent.TYPE, this);
		this.eventBus.addHandler(LoginButtonClickedEvent.TYPE, this);
		this.eventBus.addHandler(ServerStatusEvent.TYPE, this);
		this.eventBus.addHandler(AuthenticationVerificationEvent.TYPE, this);
		this.eventBus.addHandler(LoginDoneEvent.TYPE, this);
		this.eventBus.addHandler(LoginFailedEvent.TYPE, this);
		this.eventBus.addHandler(LogoutDoneEvent.TYPE, this);
	}
	
	private void logEvent(GwtEvent event){
		Log.info(event.toDebugString());
	}

	@Override
	public void afterLoginDone(DoAfterLoginEvent event) {
		logEvent(event);
	}

	@Override
	public void onDoLogin(DoLoginEvent event) {
		logEvent(event);
	}

	@Override
	public void onDoLogout(DoLogoutEvent event) {
		logEvent(event);
	}

	@Override
	public void onLoggingIn(LoggingInEvent event) {
		logEvent(event);
	}

	@Override
	public void onLoggedIn(UserLoggedInEvent event) {
		logEvent(event);
	}

	@Override
	public void onLoggedOut(UserLoggedOutEvent event) {
		logEvent(event);
	}

	@Override
	public void onGTCreatePost(GTCreatePostEvent event) {
		logEvent(event);
	}

	@Override
	public void onGTLogin(GTLoginEvent event) {
		logEvent(event);
	}

	@Override
	public void onServerStatusChange(ServerStatusEvent event) {
		logEvent(event);
	}

	@Override
	public void onLoginButtonClicked(LoginButtonClickedEvent event) {
		logEvent(event);
	}

	@Override
	public void onVerify(AuthenticationVerificationEvent event) {
		logEvent(event);
	}

	@Override
	public void onLoginDone(LoginDoneEvent event) {
		logEvent(event);
	}

	@Override
	public void onLoginFailed(LoginFailedEvent event) {
		logEvent(event);
	}

	@Override
	public void onLogoutDone(LogoutDoneEvent event) {
		logEvent(event);		
	}
 
}
