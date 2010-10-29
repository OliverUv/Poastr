package com.ollijepp.poastr.server.infrastructure;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import org.apache.commons.logging.Log;

import com.google.inject.Singleton;
import com.ollijepp.poastr.server.authentication.LoginHandler;
import com.ollijepp.poastr.server.datastorage.rpc.AddAccountHandler;
import com.ollijepp.poastr.server.datastorage.rpc.DeleteAccountHandler;
import com.ollijepp.poastr.server.datastorage.rpc.GetAccountsHandler;

/**
 * PoastrModule which binds the handlers and configurations for
 * RPC calls with gwt-dispatch
 */
public class PoastrServletModule extends ActionHandlerModule {
	@Override
	protected void configureHandlers() {
		bindHandler(LoginHandler.class);
		bindHandler(GetAccountsHandler.class);
		bindHandler(AddAccountHandler.class);
		bindHandler(DeleteAccountHandler.class);
		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
	}
}