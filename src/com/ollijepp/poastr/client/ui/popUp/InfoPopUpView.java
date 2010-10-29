package com.ollijepp.poastr.client.ui.popUp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class InfoPopUpView extends Composite implements InfoPopUpPresenter.Display
{
	private static InfoPopUpViewUiBinder uiBinder = GWT.create(InfoPopUpViewUiBinder.class);
	
	interface InfoPopUpViewUiBinder extends UiBinder<Widget, InfoPopUpView> {}
	
	@UiField
	DialogBox popUp_DialogBox;
	
	@UiField
	HTML popUp_infoHTML;
	
	@UiField
	Button popUp_closeButton;
	
	public InfoPopUpView()
	{
		initWidget(uiBinder.createAndBindUi(this));
		popUp_DialogBox.setAnimationEnabled(true);
	}

	@UiHandler("popUp_closeButton")
	void OnClick(ClickEvent event)
	{
		popUp_DialogBox.hide();
	}

	@Override
	public void setInfoMessage(String message)
	{
		popUp_infoHTML.setHTML(message);
	}
	
	@Override
	public void show() {
		// Centers and shows the dialogBox
		popUp_DialogBox.center();
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
}
