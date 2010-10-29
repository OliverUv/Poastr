package com.ollijepp.poastr.client.ui.login;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.Poastr;
import com.ollijepp.poastr.client.events.authentication.DoAfterLoginEvent;
import com.ollijepp.poastr.client.events.ui.GTCreatePostEvent;
import com.ollijepp.poastr.client.events.ui.GTLoginEvent;
import com.ollijepp.poastr.client.ui.Presenter;

@Singleton
public class LoginPresenter implements Presenter,
GTLoginEvent.Handler
{
	
	public interface Display 
	{
		Widget asWidget();
	}
	
	private final EventBus eventBus;
	private final DataAdapter dataAdapter;
	private final Display display;
	private final HasWidgets container;

	@Inject
	public LoginPresenter(EventBus eventBus, LoginPresenter.Display display, DataAdapter dataAdapter)
	{
		this.eventBus = eventBus;
		this.dataAdapter = dataAdapter;
		this.container = Poastr.getAppSpace();
		this.display = display;
		
		this.eventBus.addHandler(GTLoginEvent.TYPE, this);
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	@Override
	public void onGTLogin(GTLoginEvent event) {
		go(container);
		this.eventBus.fireEvent(new DoAfterLoginEvent(new GTCreatePostEvent()));
	}


}
