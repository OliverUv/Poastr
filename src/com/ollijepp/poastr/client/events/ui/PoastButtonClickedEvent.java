package com.ollijepp.poastr.client.events.ui;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ollijepp.poastr.client.ui.createPost.AccountWidgetPresenter;

public class PoastButtonClickedEvent extends GwtEvent<PoastButtonClickedEvent.Handler>
{
	private ArrayList<AccountWidgetPresenter> checkedAccountList = new ArrayList<AccountWidgetPresenter>();
	private String poast;
	
	public interface Handler extends EventHandler
	{
		public void onPoastButtonClicked(PoastButtonClickedEvent event);
	}

	public static Type<PoastButtonClickedEvent.Handler> TYPE = new Type<PoastButtonClickedEvent.Handler>();
	
	public PoastButtonClickedEvent(ArrayList<AccountWidgetPresenter> checkedAccountList, String poast)
	{
		this.checkedAccountList = checkedAccountList;
		this.poast = poast;
	}
	
	@Override
	protected void dispatch(PoastButtonClickedEvent.Handler handler) 
	{
		handler.onPoastButtonClicked(this);	
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PoastButtonClickedEvent.Handler> getAssociatedType() 
	{
		return TYPE;
	}

	public ArrayList<AccountWidgetPresenter> getCheckedAccountList()
	{
		return this.checkedAccountList;
	}
	
	public String getPoast()
	{
		return this.poast;
	}
}
