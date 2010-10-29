package com.ollijepp.poastr.server.authentication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dyuproject.openid.OpenIdServletFilter;
import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.YadisDiscovery;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.dyuproject.openid.ext.SRegExtension;
import com.dyuproject.util.http.UrlEncodedParameterMap;

/**
 * If authenticated, goes to the home page. If not, goes to the login page.
 */
@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {

	static {
		RelyingParty.getInstance().addListener(
				new SRegExtension().addExchange("email").addExchange("country").addExchange("language")).addListener(
				new AxSchemaExtension().addExchange("email").addExchange("country").addExchange("language"))
				.addListener(new RelyingParty.Listener() {
					public void onDiscovery(OpenIdUser user, HttpServletRequest request) {
					}

					public void onPreAuthenticate(OpenIdUser user, HttpServletRequest request,
							UrlEncodedParameterMap params) {
					}

					public void onAuthenticate(OpenIdUser user, HttpServletRequest request) {
						Map<String, String> sreg = SRegExtension.remove(user);
						Map<String, String> axschema = AxSchemaExtension.remove(user);
						if (sreg != null && !sreg.isEmpty()) {
							user.setAttribute("info", sreg);
						} else if (axschema != null && !axschema.isEmpty()) {
							user.setAttribute("info", axschema);
						}
					}

					public void onAccess(OpenIdUser user, HttpServletRequest request) {
					}
				});
	}

	RelyingParty relyingParty = RelyingParty.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String loginWith = request.getParameter("providerName");
		HttpSession session = request.getSession();
		session.setAttribute("providerName", loginWith);
		if (loginWith != null) {
			/*
			 * If the ui supplies a LoginWithGoogle or LoginWithYahoo
			 * link/button, this will speed up the openid process by skipping
			 * discovery. The override is done by adding the OpenIdUser to the
			 * request attribute.
			 */
			if (loginWith.equals("google")) {
				OpenIdUser user = OpenIdUser.populate("https://www.google.com/accounts/o8/id",
						YadisDiscovery.IDENTIFIER_SELECT, "https://www.google.com/accounts/o8/ud");
				request.setAttribute(OpenIdUser.ATTR_NAME, user);

			} else if (loginWith.equals("yahoo")) {
				OpenIdUser user = OpenIdUser.populate("http://yahoo.com/", YadisDiscovery.IDENTIFIER_SELECT,
						"https://open.login.yahooapis.com/openid/op/auth");
				request.setAttribute(OpenIdUser.ATTR_NAME, user);
			}
		}

		String errorMsg = OpenIdServletFilter.DEFAULT_ERROR_MSG;
		try {
			OpenIdUser user = relyingParty.discover(request);
			if (user == null) {
				if (RelyingParty.isAuthResponse(request)) {
					// authentication timeout
					response.sendRedirect(request.getRequestURI());
				} else {
					// set error msg if the openid_identifier is not resolved.
					if (request.getParameter(relyingParty.getIdentifierParameter()) != null)
						request.setAttribute(OpenIdServletFilter.ERROR_MSG_ATTR, errorMsg);

					// new user
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
				return;
			}

			if (user.isAuthenticated()) {
				//request.getRequestDispatcher("/Poastr.html").forward(request, response);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			if (user.isAssociated() && RelyingParty.isAuthResponse(request)) {
				// verify authentication
				if (relyingParty.verifyAuth(user, request, response)) {
					response.sendRedirect(request.getContextPath() + "/home/");
				} else {
					// failed verification
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
				return;
			}

			// associate and authenticate user
			StringBuffer url = request.getRequestURL();
			String trustRoot = url.substring(0, url.indexOf("/", 9));
			String realm = url.substring(0, url.lastIndexOf("/"));
			String returnTo = url.toString();
			if (relyingParty.associateAndAuthenticate(user, request, response, trustRoot, realm, returnTo)) {
				// successful association
				return;
			}
		} catch (UnknownHostException uhe) {
			errorMsg = OpenIdServletFilter.ID_NOT_FOUND_MSG;
		} catch (FileNotFoundException fnfe) {
			errorMsg = OpenIdServletFilter.DEFAULT_ERROR_MSG;
		} catch (Exception e) {
			errorMsg = OpenIdServletFilter.DEFAULT_ERROR_MSG;
		}
		request.setAttribute(OpenIdServletFilter.ERROR_MSG_ATTR, errorMsg);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
}