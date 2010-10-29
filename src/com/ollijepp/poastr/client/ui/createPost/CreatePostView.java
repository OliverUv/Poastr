package com.ollijepp.poastr.client.ui.createPost;

import java.util.List;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.crypto.ClipperzCrypto;
import com.ollijepp.poastr.client.events.ui.AddAccountButtonClickedEvent;
import com.ollijepp.poastr.client.events.ui.PoastButtonClickedEvent;
import com.ollijepp.poastr.client.events.ui.PoastButtonClickedEvent.Handler;
import com.ollijepp.poastr.shared.socialService.SocialService;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;

@Singleton
public class CreatePostView extends Composite implements CreatePostPresenter.Display{

	private static CreatePostViewUiBinder uiBinder = GWT
			.create(CreatePostViewUiBinder.class);
	
	interface CreatePostViewUiBinder extends UiBinder<Widget, CreatePostView> {
	}

	@UiField
	VerticalPanel createPost_Panel;
	
	@UiField
	TextArea createPost_postText;
	
	@UiField
	Label createPost_wordCount;
	
	@UiField
	Label createPost_userInfo;
	
	@UiField
	Button createPost_poast;
	
	@UiField
	VerticalPanel createPost_accountsPanel;
	
	@UiField
	Button createPost_addAccountButton;

	@Inject
	public CreatePostView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget()
	{
		return this;
	}

	@UiHandler("createPost_postText")
	void onKeyUp(KeyUpEvent event)
	{
		String text = createPost_postText.getText();
		int nrOfChars = text.length();
		createPost_wordCount.setText(Integer.toString(nrOfChars));
	}
	
	@UiHandler("createPost_poast")
	void handleClick(ClickEvent event)
	{	
		ArrayList<AccountWidgetPresenter> checkedWidgetList = new ArrayList<AccountWidgetPresenter>();
		for (Widget accountWidget : createPost_accountsPanel)
		{
			if (((AccountWidgetPresenter) accountWidget).isChecked())
			{
				checkedWidgetList.add((AccountWidgetPresenter) accountWidget);
			}
		}
		
		fireEvent(new PoastButtonClickedEvent(checkedWidgetList, createPost_postText.getText()));
	}
	
	@UiHandler("createPost_addAccountButton")
	void OnClick(ClickEvent event)
	{
		fireEvent(new AddAccountButtonClickedEvent());
	}
	
	@Override
	public HasValue<String> getPostText() {
		return createPost_postText;
	}
	
	@Override
	public void setShowUser(String userInfo){
		createPost_userInfo.setText(userInfo);
	}
	
	@Override
	public void setSocialServices(ArrayList<AccountWidgetPresenter> socialServiceList)
	{
			createPost_accountsPanel.clear();
			
			for(AccountWidgetPresenter service : socialServiceList)
			{
				createPost_accountsPanel.add(service);
			}
	}

	@Override
	public HandlerRegistration addPoastButtonClickedEventHandler(
			PoastButtonClickedEvent.Handler handler) 
	{
		return addHandler(handler, PoastButtonClickedEvent.TYPE);
	}

	@Override
	public HandlerRegistration addAddAccountButtonClickedEventHandler(
			AddAccountButtonClickedEvent.Handler handler) {
		return addHandler(handler, AddAccountButtonClickedEvent.TYPE);
	}
	
	
}
