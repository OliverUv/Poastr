package com.ollijepp.poastr.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.ollijepp.poastr.client.events.authentication.CheckSessionEvent;
import com.ollijepp.poastr.client.events.ui.GTCreatePostHistorizer;
import com.ollijepp.poastr.client.events.ui.GTLoginEvent;
import com.ollijepp.poastr.client.events.ui.GTLoginEventHistorizer;
import com.ollijepp.poastr.client.exceptions.history.EventNotHistorizableException;
import com.ollijepp.poastr.client.exceptions.history.EventTypeAlreadyRegisteredException;
import com.ollijepp.poastr.client.exceptions.history.IdUsedException;
import com.ollijepp.poastr.client.history.PoastrHistoryHandler;
import com.ollijepp.poastr.client.infrastructure.PoastrInjector;
import com.ollijepp.poastr.client.ui.LoginLogout.LoginLogoutPresenter;
import com.ollijepp.poastr.client.ui.createPost.CreatePostPresenter;
import com.ollijepp.poastr.client.ui.login.LoginPresenter;
import com.ollijepp.poastr.client.ui.popUp.AddAccountPopUpPresenter;
import com.ollijepp.poastr.client.ui.popUp.InfoPopUpPresenter;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Poastr implements EntryPoint{
	
	/*private final EventBus eventBus;
	private final PoastrHistoryHandler history;
	private final DispatchAsync dispatcher;*/
	private final PoastrInjector injector = GWT.create(PoastrInjector.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		/* *
		 * Initialize the application. The order in which things are
		 * created matters, as some things send events to each other
		 * when starting up.
		 * 
		 * */
		
		
		//These are necessary for the presenters to be created,
		//so that they can listen for events. Should be replaced
		//with a class listening to all events and loading their
		//respective presenters lazily.

		EventBus eventBus = injector.getEventBus(); 
		DataAdapter da = injector.getDataAdapter();
		AuthManager auth = injector.getAuthManager();
		PoastrHistoryHandler history = injector.getHistoryHandler();
		SocialServiceFactory ssFact = injector.getSocialServiceFactory();
		PoastPoaster poastr = injector.getPoastPoaster();
		
		CreatePostPresenter c = injector.getCreatePostPresenter();
		LoginPresenter start = injector.getStartPresenter();
		LoginLogoutPresenter loginLogout = injector.getLoginLogoutPresenter();
		InfoPopUpPresenter infoPop = injector.getInfoPopUpPresenter();
		AddAccountPopUpPresenter addPop = injector.getAddAccountPopUpPresenter();
		try {
			//Choose events to historize, for this to work, history must
			//also implement the Handler interfaces. See PoastrHistoryHandler.
			history.registerEventType(new GTLoginEventHistorizer());
			history.registerEventType(new GTCreatePostHistorizer());
			
			//Make sure everything that needs to know if the user is
			//logged in or out knows it.
			eventBus.fireEvent(new CheckSessionEvent());
			
			/*Start application by firing first possible Historizble event.
			  Note that it is not certain that this is where the app goes at
			  startup, only that it goes there if no history is in the url. */
			history.startApplication(new GTLoginEvent());
			
		} catch (IdUsedException e) {
			e.printStackTrace();
			return;
		} catch (EventNotHistorizableException e){
			e.printStackTrace();
			return;
		} catch (EventTypeAlreadyRegisteredException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public static HasWidgets getAppSpace(){
		return RootPanel.get("applicationspace");
	}

	public static HasWidgets getLoginLogoutSpace() {
		return RootPanel.get("loginlogoutspace");
	}
}
