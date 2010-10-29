package com.ollijepp.poastr.server.authentication;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mortbay.util.ajax.JSON;

import com.dyuproject.openid.Constants;
import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.util.http.UrlEncodedParameterMap;

/**
 * @see <a
 *      href="http://www.sociallipstick.com/2009/02/how-to-accept-openid-in-a-pop
 *      u p -without-leaving-the-page/">Based on this.</a>
 */
@SuppressWarnings("serial")
public class PopupVerifyServlet extends HttpServlet {

	RelyingParty _relyingParty = RelyingParty.getInstance().addListener(new RelyingParty.Listener() {
		public void onAccess(OpenIdUser user, HttpServletRequest request) {
		}

		public void onAuthenticate(OpenIdUser user, HttpServletRequest request) {
		}

		public void onDiscovery(OpenIdUser user, HttpServletRequest request) {
		}

		public void onPreAuthenticate(OpenIdUser user, HttpServletRequest request, UrlEncodedParameterMap params) {
			// the popup sign-in magic
			if ("true".equals(request.getParameter("popup"))) {
				String returnTo = params.get(Constants.OPENID_TRUST_ROOT) + request.getContextPath()
						+ "/popup_verify.html";
				params.put(Constants.OPENID_RETURN_TO, returnTo);
				params.put(Constants.OPENID_REALM, returnTo);
				params.put("openid.ns.ui", "http://specs.openid.net/extensions/ui/1.0");
				params.put("openid.ui.mode", "popup");
			}
		}
	});

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			OpenIdUser openIdUser = _relyingParty.discover(request);
			if (openIdUser != null) {
				if (openIdUser.isAuthenticated()
						|| (openIdUser.isAssociated() && RelyingParty.isAuthResponse(request) && _relyingParty
								.verifyAuth(openIdUser, request, response))) {
					/* Session stuff for openid provider selection */
					HttpSession session = request.getSession();
					response.setContentType("text/plain");
					response.getWriter().write(openIdUser.getIdentity());
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setStatus(401);
	}
}
