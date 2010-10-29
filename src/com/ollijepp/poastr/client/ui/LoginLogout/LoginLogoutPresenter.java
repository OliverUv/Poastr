package com.ollijepp.poastr.client.ui.LoginLogout;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.Poastr;
import com.ollijepp.poastr.client.events.authentication.DoAfterLoginEvent;
import com.ollijepp.poastr.client.events.authentication.DoLoginEvent;
import com.ollijepp.poastr.client.events.authentication.DoLogoutEvent;
import com.ollijepp.poastr.client.events.authentication.UserLoggedInEvent;
import com.ollijepp.poastr.client.events.authentication.UserLoggedOutEvent;
import com.ollijepp.poastr.client.events.authentication.LoggingInEvent;
import com.ollijepp.poastr.client.events.ui.GTCreatePostEvent;
import com.ollijepp.poastr.client.events.ui.GTLoginEvent;
import com.ollijepp.poastr.client.events.ui.InfoPopUpEvent;
import com.ollijepp.poastr.client.events.ui.LoginButtonClickedEvent;
import com.ollijepp.poastr.client.events.ui.QuestionMarkClickedEvent;
import com.ollijepp.poastr.client.ui.Presenter;
import com.ollijepp.poastr.shared.events.authentication.LoginFailedEvent;

@Singleton
public class LoginLogoutPresenter implements Presenter,
GTLoginEvent.Handler,
UserLoggedInEvent.Handler,
UserLoggedOutEvent.Handler,
LoggingInEvent.Handler,
LoginFailedEvent.Handler,
LoginButtonClickedEvent.Handler,
QuestionMarkClickedEvent.Handler
{
	
	public interface Display 
	{
		HasClickHandlers loginButton();
		Widget asWidget();
		HandlerRegistration addLoginButtonClickedHandler(LoginButtonClickedEvent.Handler handler);
		HandlerRegistration addQuestionMarkClickedHandler(QuestionMarkClickedEvent.Handler handler);
		void makeLoginFailed();
		void makeButtonLogin();
		void makeButtonLogout();
		void showLoggingIn();
	}
	
	private enum ButtonState { doLogin, doLogout, logginIn }
	
	private final EventBus eventBus;
	private final DataAdapter dataAdapter;
	private final Display display;
	private final HasWidgets container;
	private ButtonState buttonState;
	
	@Inject
	public LoginLogoutPresenter(EventBus eventBus, LoginLogoutPresenter.Display display, DataAdapter dataAdapter)
	{
		this.eventBus = eventBus;
		this.dataAdapter = dataAdapter;
		this.container = Poastr.getLoginLogoutSpace();
		this.display = display;
		
		this.display.addLoginButtonClickedHandler(this);
		this.display.addQuestionMarkClickedHandler(this);
		
		this.eventBus.addHandler(GTLoginEvent.TYPE, this);
		this.eventBus.addHandler(LoginFailedEvent.TYPE, this);
		this.eventBus.addHandler(LoggingInEvent.TYPE, this);
		this.eventBus.addHandler(UserLoggedInEvent.TYPE, this);
		this.eventBus.addHandler(UserLoggedOutEvent.TYPE, this);
		
		if (this.dataAdapter.hasUserInfo()) {
			this.display.makeButtonLogout();
			this.buttonState = ButtonState.doLogout;
		} else {
			this.display.makeButtonLogin();
			this.buttonState = ButtonState.doLogin;
		}
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	
	private void loginLogout() {
		switch (buttonState){
		case doLogin:
			doLogin(); break;
		case doLogout:
			doLogout(); break;
		case logginIn:
			Window.alert("Alread trying to log in"); break;
			//this probably needs to be replaced with something
			//useful, such as reshowing the openid popup.. maybe.
		default:
			System.out.println("LoginLogoutPresenter in undefined state.");
			buttonState = ButtonState.doLogin;
		}
	}
	
	private void doLogin() {
		if(dataAdapter.hasUserInfo()){
			eventBus.fireEvent(new GTCreatePostEvent());
		}else{
			eventBus.fireEvent(new DoLoginEvent());
		}
	}
	
	private void doLogout() {
		eventBus.fireEvent(new DoLogoutEvent());
		eventBus.fireEvent(new GTLoginEvent());
	}
	
	@Override
	public void onLoginButtonClicked(LoginButtonClickedEvent event) {
		loginLogout();
	}

	@Override
	public void onGTLogin(GTLoginEvent event) {
		go(container);
	}

	@Override
	public void onLoggingIn(LoggingInEvent event) {
		// show logging in
		buttonState = ButtonState.logginIn;
		display.showLoggingIn();
	}
	
	@Override
	public void onLoginFailed(LoginFailedEvent event) {
		// show login failed message and login
		System.out.println("Login failed: " + event.toDebugString());
		buttonState = ButtonState.doLogin;
		display.makeLoginFailed();
	}

	@Override
	public void onLoggedIn(UserLoggedInEvent event) {
		// show logout
		buttonState = ButtonState.doLogout;
		display.makeButtonLogout();
	}

	@Override
	public void onLoggedOut(UserLoggedOutEvent event) {
		// show login
		buttonState = ButtonState.doLogin;
		display.makeButtonLogin();
	}
	
	@Override
	public void onQuestionMarkClicked(QuestionMarkClickedEvent event) 
	{
		this.eventBus.fireEvent(new InfoPopUpEvent(event.getInfoMessage()));
	}
}
