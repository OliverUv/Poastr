package com.ollijepp.poastr.server.infrastructure;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * Configures guice's magic rpc handling to work with gwt-dispatch. 
 * @author Oliver Uvman
 *
 */
public class PoastrGuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new PoastrServletModule(), new DispatchServletModule());
	}
}