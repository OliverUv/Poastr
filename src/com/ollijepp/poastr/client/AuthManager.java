package com.ollijepp.poastr.client;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.events.authentication.DoLoginEvent;
import com.ollijepp.poastr.client.events.authentication.DoLogoutEvent;
import com.ollijepp.poastr.client.events.authentication.LoggingInEvent;
import com.ollijepp.poastr.client.events.authentication.UserLoggedInEvent;
import com.ollijepp.poastr.client.events.authentication.UserLoggedOutEvent;
import com.ollijepp.poastr.client.events.authentication.DoAfterLoginEvent;
import com.ollijepp.poastr.client.events.authentication.CheckSessionEvent;
import com.ollijepp.poastr.client.infrastructure.GwtCallback;
import com.ollijepp.poastr.shared.authentication.CheckSession;
import com.ollijepp.poastr.shared.authentication.CheckSessionResult;
import com.ollijepp.poastr.shared.events.authentication.AuthenticationVerificationEvent;
import com.ollijepp.poastr.shared.events.authentication.LoginDoneEvent;
import com.ollijepp.poastr.shared.events.authentication.LoginFailedEvent;
import com.ollijepp.poastr.shared.events.authentication.LogoutDoneEvent;

/**
 * This class provides programmatic access of user authentication
 * and session handling to there rest of the application.
 * 
 * It works together with some servlets, java server pages, javascript
 * and other magic. Any changes should be tested thoroughly.
 * 
 * TODO: Make sure login from other place than Login should not auto-redirect to CreatePost
 * 
 * @author Oliver Uvman
 *
 */
@Singleton
public class AuthManager implements
DoLoginEvent.Handler,
DoLogoutEvent.Handler,
AuthenticationVerificationEvent.Handler,
DoAfterLoginEvent.Handler,
CheckSessionEvent.Handler {
	
	private static EventBus eventBus; //Don't consider this static.
	private final DispatchAsync dispatcher;
	private GwtEvent<?> afterSuccess;
	
	@Inject
	public AuthManager(EventBus eventBus, DispatchAsync dispatcher) {
		AuthManager.eventBus = eventBus;
		this.dispatcher = dispatcher;
		this.afterSuccess = new LoginDoneEvent();
		registerJSMethod(); // registers method to handle openId responses
		
		eventBus.addHandler(DoLoginEvent.TYPE, this); //doLogin
		eventBus.addHandler(DoLogoutEvent.TYPE, this); //doLogout
		eventBus.addHandler(DoAfterLoginEvent.TYPE, this); //afterLoginDone
		eventBus.addHandler(AuthenticationVerificationEvent.TYPE, this); //onVerify
		eventBus.addHandler(CheckSessionEvent.TYPE, this); //onCheckSession
}

	/**
	 * Checks if the user is already logged in
	 */
	public void checkSession() {
		dispatcher.execute(new CheckSession(), new GwtCallback<CheckSessionResult>(dispatcher, eventBus) {
			@Override
			public void callback(CheckSessionResult result) {
				if (result.isValid()) {
					String userOpenId = getOpenIdFromJSON(result.getUserOpenId());
					eventBus.fireEvent(new UserLoggedInEvent(userOpenId));
					//eventBus.fireEvent(afterSuccess);
				} else {
					eventBus.fireEvent(new UserLoggedOutEvent());
				}
			}
		});
	}

	protected void doLogout() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/logout/");
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert("Error logging out:" + exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					Log.info("Logout firing UserLoggedOut");
					eventBus.fireEvent(new UserLoggedOutEvent());
					Log.info("Logout firing LogoutDone");
					eventBus.fireEvent(new LogoutDoneEvent());
				}
			});
		} catch (RequestException e) {
			Log.error("Unable to build the request for logout");
		}
		//eventBus.fireEvent(new LogoutDoneEvent());
	}

	protected void doLogin() {
		eventBus.fireEvent(new LoggingInEvent());
		int top = Window.getClientHeight() / 2 - 125;
		int left = Window.getClientWidth() / 2 - 250;
		String features = "status=0,toolbar=0,menubar=0,location=0,resizable=1,width=600,height=400,top=" + top
				+ ",left=" + left;
		Window.open("/home/?popup=true", "_blank", features);
	}

	public void onVerify(AuthenticationVerificationEvent event) {
		if (event.isVerified()) {
			eventBus.fireEvent(new UserLoggedInEvent(event.getUserOpenId()));
			eventBus.fireEvent(afterSuccess);
		} else {
			System.out.println("Verification Failed");
			eventBus.fireEvent(new UserLoggedOutEvent());
			eventBus.fireEvent(new LoginFailedEvent());
		}
	}
	
	/**
	 * Add a GWT javascript hook for OpenID callback
	 */
	private native void registerJSMethod() /*-{
		$wnd.handleOpenIDResponse = @com.ollijepp.poastr.client.AuthManager::handleOpenIDResponse(Ljava/lang/String;);
	}-*/;

	/**
	 * Parses the OpenID response to extract the identity
	 */
	public static void handleOpenIDResponse(String response) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/popup_verify/?" + response);
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert("Error verifying:" + exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 401) {
						eventBus.fireEvent(new AuthenticationVerificationEvent(false, null));
					} else {
						String openId = getOpenIdFromJSON(response.getText());
						eventBus.fireEvent(new AuthenticationVerificationEvent(true, openId));
					}
				}
			});
		} catch (RequestException e) {
			Window.alert("Unable to build the request.");
		}
	}

	protected static String getOpenIdFromJSON(String text) {
		return text;
	}

	@Override
	public void onDoLogin(DoLoginEvent event) {
		doLogin();
	}

	@Override
	public void onDoLogout(DoLogoutEvent event) {
		doLogout();
	}

	@Override
	public void afterLoginDone(DoAfterLoginEvent event) {
		if(event.eventToDoAfter() != null){
			afterSuccess = event.eventToDoAfter();
		} else {
			afterSuccess = new LoginDoneEvent();
		}
	}

	@Override
	public void onCheckSession(CheckSessionEvent event) {
		checkSession();
	}
}