package com.ollijepp.poastr.client.ui.LoginLogout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.ollijepp.poastr.client.events.ui.GTCreatePostEvent;
import com.ollijepp.poastr.client.events.ui.LoginButtonClickedEvent;
import com.ollijepp.poastr.client.events.ui.QuestionMarkClickedEvent;
import com.ollijepp.poastr.client.events.ui.LoginButtonClickedEvent.Handler;

public class LoginLogoutView extends Composite implements LoginLogoutPresenter.Display{

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginLogoutView> {}

	@UiField
	Button loginLogoutButton;
	
	@UiField
	Image logIn_logInInfo;
	
	public LoginLogoutView() 
	{
		
		initWidget(uiBinder.createAndBindUi(this));
		logIn_logInInfo.setUrl(GWT.getModuleBaseURL() + "images/questionmark.png");
	}
	
	public Widget asWidget()
	{
		return this;
	}
	
	@UiHandler("loginLogoutButton")
	void handleClick(ClickEvent event)
	{
		fireEvent(new LoginButtonClickedEvent());
	}
	
	@UiHandler("logIn_logInInfo")
	void OnClick(ClickEvent event)
	{
		String message = "No information about the user account is stored in <br> our database";
		fireEvent(new QuestionMarkClickedEvent(message));
	}

	@Override
	public HandlerRegistration addQuestionMarkClickedHandler(QuestionMarkClickedEvent.Handler handler) {
		return addHandler(handler, QuestionMarkClickedEvent.TYPE);
	}

	@Override
	public HandlerRegistration addLoginButtonClickedHandler(LoginButtonClickedEvent.Handler handler) {
		return addHandler(handler, LoginButtonClickedEvent.TYPE);
	}

	@Override
	public HasClickHandlers loginButton() {
		return loginLogoutButton;
	}

	@Override
	public void makeButtonLogin() {
		loginLogoutButton.setText("Login");
		loginLogoutButton.setTitle("Login");
		loginLogoutButton.setEnabled(true);
	}

	@Override
	public void makeButtonLogout() {
		loginLogoutButton.setText("Logout");
		loginLogoutButton.setTitle("Logout");
		loginLogoutButton.setEnabled(true);
	}

	@Override
	public void showLoggingIn() {
		loginLogoutButton.setText("Logging in");
		loginLogoutButton.setTitle("Logging in");
		loginLogoutButton.setEnabled(false);
	}

	@Override
	public void makeLoginFailed() {
		Window.alert("Login Failed!");
	}
}
