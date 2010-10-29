package com.ollijepp.poastr.client.ui.popUp;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ollijepp.poastr.client.events.ui.PopUpAddAccountButtonClickedEvent;
import com.ollijepp.poastr.client.events.ui.PopUpAddAccountButtonClickedEvent.Handler;
import com.ollijepp.poastr.shared.socialService.Facebook;
import com.ollijepp.poastr.shared.socialService.ServiceProvider;
import com.ollijepp.poastr.shared.socialService.SocialService;

public class AddAccountPopUpView extends Composite implements AddAccountPopUpPresenter.Display
{

	private static AddAccountPopUpViewUiBinder uiBinder = GWT
			.create(AddAccountPopUpViewUiBinder.class);

	interface AddAccountPopUpViewUiBinder extends
			UiBinder<Widget, AddAccountPopUpView> {
	}

	@UiField
	DialogBox popUp_DialogBox;
	
	@UiField
	VerticalPanel popUp_Panel;
	
	@UiField
	HorizontalPanel addPopUp_socialServiceLogosPanel;
	
	@UiField
	Label addPopUp_usernameLabel;
	
	@UiField
	Label addPopUp_passwordLabel;
	
	@UiField
	TextBox addPopUp_usernameTextBox;
	
	@UiField
	PasswordTextBox addPopUp_PasswordTextBox;
	
	private ServiceProvider activeProvider = null;
	
	/**
	 * Constructor
	 */
	public AddAccountPopUpView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("addPopUp_addAccountButton")
	void handlerClick(ClickEvent event)
	{
		/*
		 * TODO?: Specify checkUsername, checkPassword in provider which would prevent a possible huge if-block 
		 */
		if (activeProvider == null) {return;}
		String username = addPopUp_usernameTextBox.getValue();
		String password = addPopUp_PasswordTextBox.getValue();
		if (username.isEmpty()) {return;}
		// If no password is required, the user shouldn't be punished, in that case; more checks needed
		if (password.isEmpty()) {return;} 
		ServiceProvider serviceProvider  = activeProvider;
		
		fireEvent(new PopUpAddAccountButtonClickedEvent(username, password, serviceProvider));
		OnClick(event);
	}
	
	@UiHandler("popUp_closeButton")
	void OnClick(ClickEvent event)
	{
		// Clear and hide
		popUp_DialogBox.hide();
		addPopUp_usernameTextBox.setText("");
		addPopUp_PasswordTextBox.setText("");
		popUp_Panel.setStyleName("popUp_Panel");
		popUp_Panel.addStyleName("DefaultColor");
	}
	
	@Override
	public Widget asWidget() 
	{
		return this;
	}

	@Override
	public void show() 
	{
		popUp_DialogBox.center();
	}

	@Override
	public HandlerRegistration addPopUpAddAccountButtonClickedEventHandler(
			Handler handler) 
	{
		return addHandler(handler, PopUpAddAccountButtonClickedEvent.TYPE);
	}

	@Override
	public void setSocialServiceLogos(List<ServiceProvider> serviceProviderList) 
	{
		for (final ServiceProvider provider : serviceProviderList)
		{
			//this.serviceProviderList.add(provider);
			Image logo = new Image();
			logo.setStyleName("addPopUp_logo");
			logo.setUrl(GWT.getModuleBaseURL() + "images/" + provider.getLogoPath());
			logo.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) 
				{
					activeProvider = provider;
					popUp_Panel.setStyleName("popUp_Panel");
					popUp_Panel.addStyleName(provider.getName());
				}
				
			});
			
			addPopUp_socialServiceLogosPanel.add(logo);
		}
	}

}
