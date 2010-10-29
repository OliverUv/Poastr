package com.ollijepp.poastr.server.infrastructure;

import net.customware.gwt.dispatch.server.service.DispatchServiceServlet;

import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {
	@Override
	public void configureServlets() {
		serve("/poastr/dispatch").with(DispatchServiceServlet.class);
	}
}