package com.ollijepp.poastr.client.ui.popUp;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.Poastr;
import com.ollijepp.poastr.client.events.ui.InfoPopUpEvent;
import com.ollijepp.poastr.client.ui.Presenter;

@Singleton
public class InfoPopUpPresenter implements Presenter, InfoPopUpEvent.Handler
{
	public interface Display
	{
		void setInfoMessage(String message);
		void show();
		Widget asWidget();
	}
	
	private final HandlerManager eventBus;
	private final Display display;
	private final HasWidgets container;
	private final DataAdapter dataAdapter;
	
	@Inject
	public InfoPopUpPresenter(EventBus eventBus, Display display, DataAdapter dataAdapter) 
	{
		this.eventBus = eventBus;
		this.display = display;
		this.dataAdapter = dataAdapter;
		this.container = Poastr.getAppSpace();
		
		// Listen to this event on the eventBus
		this.eventBus.addHandler(InfoPopUpEvent.TYPE, this);
	}

	@Override
	public void onInfoPopUp(InfoPopUpEvent event) 
	{
		display.setInfoMessage(event.getInfoMessage());
		go(this.container);
	}

	@Override
	public void go(HasWidgets container) 
	{
		display.show();	
	}

}
