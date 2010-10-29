package com.ollijepp.poastr.client.ui.createPost;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.ollijepp.poastr.client.events.ui.DeleteAccountClickedEvent;
//import com.ollijepp.poastr.client.events.ui.DeleteAccountClickedEvent.Handler;

public class AccountWidgetView extends Composite implements AccountWidgetPresenter.Display
{

	private static AccountWidgetViewUiBinder uiBinder = GWT
			.create(AccountWidgetViewUiBinder.class);

	interface AccountWidgetViewUiBinder extends
			UiBinder<Widget, AccountWidgetView> {
	}

	@UiField
	Image createPost_logo;
	
	@UiField
	Image createPost_cross;
	
	@UiField
	CheckBox createPost_CheckBox;
	
	@UiField
	Label createPost_accountInfoLabel;
	
	public AccountWidgetView(String username, String logoPath) 
	{
		initWidget(uiBinder.createAndBindUi(this));
		createPost_cross.setUrl(GWT.getModuleBaseURL() + "images/cross.png");
		createPost_logo.setUrl(GWT.getModuleBaseURL() + "images/" + logoPath);
		createPost_accountInfoLabel.setText(username);
	}

	@UiHandler("createPost_cross")
	void OnClick(ClickEvent event)
	{
		fireEvent(new DeleteAccountClickedEvent());
	}
	
	@Override
	public Widget asWidget() 
	{
		return this;
	}

	@Override
	public HandlerRegistration addDeleteAccountClickedEventHandler(
			DeleteAccountClickedEvent.Handler handler) {
		return addHandler(handler, DeleteAccountClickedEvent.TYPE);
	}

	@Override
	public boolean isChecked() 
	{
		return createPost_CheckBox.getValue();
	}

}
