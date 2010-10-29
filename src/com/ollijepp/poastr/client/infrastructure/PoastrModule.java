package com.ollijepp.poastr.client.infrastructure;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.googlecode.objectify.ObjectifyFactory;
import com.ollijepp.poastr.client.AuthManager;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.EventLogger;
import com.ollijepp.poastr.client.PoastPoaster;
import com.ollijepp.poastr.client.history.PoastrHistoryHandler;
import com.ollijepp.poastr.client.ui.LoginLogout.LoginLogoutPresenter;
import com.ollijepp.poastr.client.ui.LoginLogout.LoginLogoutView;
import com.ollijepp.poastr.client.ui.createPost.CreatePostPresenter;
import com.ollijepp.poastr.client.ui.createPost.CreatePostView;
import com.ollijepp.poastr.client.ui.login.LoginPresenter;
import com.ollijepp.poastr.client.ui.login.LoginView;
import com.ollijepp.poastr.client.ui.popUp.AddAccountPopUpPresenter;
import com.ollijepp.poastr.client.ui.popUp.AddAccountPopUpView;
import com.ollijepp.poastr.client.ui.popUp.InfoPopUpPresenter;
import com.ollijepp.poastr.client.ui.popUp.InfoPopUpView;
import com.ollijepp.poastr.shared.socialService.SocialService;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;

/**
 * Gin configuration, here we fill class dependencies.
 */
public class PoastrModule extends AbstractGinModule {
	@Override
	protected void configure() {
		
		//For some reason we have to Singleton everything even though
		//they have been annotated with @Singleton, pfft.
		
		//bind(EventLogger.class).in(Singleton.class);
		bind(EventBus.class).in(Singleton.class);
		bind(PoastrHistoryHandler.class).in(Singleton.class);
		bind(DataAdapter.class).in(Singleton.class);
		bind(AuthManager.class).in(Singleton.class);
		bind(SocialServiceFactory.class).in(Singleton.class);
		bind(PoastPoaster.class).in(Singleton.class);
		
		//Presenter bindings
		bind(LoginPresenter.class).in(Singleton.class);
		bind(LoginPresenter.Display.class).to(LoginView.class).in(Singleton.class);
		
		bind(CreatePostPresenter.class).in(Singleton.class);
		bind(CreatePostPresenter.Display.class).to(CreatePostView.class).in(Singleton.class);
		
		bind(LoginLogoutPresenter.class).in(Singleton.class);
		bind(LoginLogoutPresenter.Display.class).to(LoginLogoutView.class).in(Singleton.class);
		
		bind(InfoPopUpPresenter.class).in(Singleton.class);
		bind(InfoPopUpPresenter.Display.class).to(InfoPopUpView.class).in(Singleton.class);
		
		bind(AddAccountPopUpPresenter.class).in(Singleton.class);
		bind(AddAccountPopUpPresenter.Display.class).to(AddAccountPopUpView.class).in(Singleton.class);

	}
}