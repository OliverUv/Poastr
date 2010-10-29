package com.ollijepp.poastr.client.infrastructure;

import net.customware.gwt.dispatch.client.gin.ClientDispatchModule;


import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.ollijepp.poastr.client.AuthManager;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.EventLogger;
import com.ollijepp.poastr.client.PoastPoaster;
import com.ollijepp.poastr.client.ui.LoginLogout.LoginLogoutPresenter;
import com.ollijepp.poastr.client.history.PoastrHistoryHandler;
import com.ollijepp.poastr.client.ui.createPost.CreatePostPresenter;
import com.ollijepp.poastr.client.ui.login.LoginPresenter;
import com.ollijepp.poastr.client.ui.popUp.AddAccountPopUpPresenter;
import com.ollijepp.poastr.client.ui.popUp.InfoPopUpPresenter;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;

@GinModules( { ClientDispatchModule.class, PoastrModule.class })
public interface PoastrInjector extends Ginjector {
	PoastrHistoryHandler getHistoryHandler();
	LoginPresenter getStartPresenter();
	CreatePostPresenter getCreatePostPresenter();
	AuthManager getAuthManager();
	DataAdapter getDataAdapter();
	LoginLogoutPresenter getLoginLogoutPresenter();
	InfoPopUpPresenter getInfoPopUpPresenter();
	AddAccountPopUpPresenter getAddAccountPopUpPresenter();
	EventBus getEventBus();
	SocialServiceFactory getSocialServiceFactory();
	PoastPoaster getPoastPoaster();
}