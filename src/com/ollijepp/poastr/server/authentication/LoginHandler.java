package com.ollijepp.poastr.server.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.google.inject.Inject;
import com.google.inject.Provider;
//import com.google.inject.Inject;
//import com.google.inject.Provider;
import com.ollijepp.poastr.shared.authentication.CheckSession;
import com.ollijepp.poastr.shared.authentication.CheckSessionResult;

public class LoginHandler implements ActionHandler<CheckSession, CheckSessionResult> {
	private final Provider<HttpServletRequest> request;
	private RelyingParty relyingParty = RelyingParty.getInstance();

	@Inject
	public LoginHandler(final Provider<HttpServletRequest> request) {
		this.request = request;
	}

	@Override
	public CheckSessionResult execute(CheckSession action, ExecutionContext context) throws ActionException {
		CheckSessionResult retVal = new CheckSessionResult();
		try {
			OpenIdUser openIdUser = relyingParty.discover(request.get());
			if (openIdUser != null && openIdUser.isAuthenticated()) {
				HttpSession session = request.get().getSession();
				session.getId();
				retVal.setUserOpenId(openIdUser.getIdentity());
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return retVal;
	}

	@Override
	public Class<CheckSession> getActionType() {
		return CheckSession.class;
	}

	@Override
	public void rollback(CheckSession action, CheckSessionResult result, ExecutionContext context)
			throws ActionException {
	}
}
