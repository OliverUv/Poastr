package com.ollijepp.poastr.server.oauth;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.dyuproject.oauth.Token;
import com.dyuproject.oauth.TokenManager;
import com.googlecode.objectify.Key;
import com.ollijepp.poastr.server.datastorage.dao.OAuthTokenDAO;
import com.ollijepp.poastr.server.exceptions.oauth.NoSuchTokenException;
import com.ollijepp.poastr.shared.socialService.AccountOwner;

public class GAEBasedTokenManager implements TokenManager {
	private static final Logger log = Logger.getLogger(GAEBasedTokenManager.class);

	@Override
	public Token getToken(String consumerKey, HttpServletRequest request)
			throws IOException {
		Log.info("GAEBTM: accountOwnerHashed: " + (String)request.getParameter("accountOwnerHashed"));
		Log.info("GAEBTM: serviceProvider: " + (String)request.getParameter("serviceProvider"));
		OAuthTokenDAO dao = new OAuthTokenDAO();
		Key<AccountOwner> accountOwnerKey = new Key<AccountOwner>(AccountOwner.class, (String)request.getParameter("accountOwnerHashed"));
		Token returnToken = null;
		try {
			returnToken = dao.getToken(
							accountOwnerKey,
							(String)request.getParameter("serviceProvider"),
							consumerKey).getOAuthToken();
		} catch (NoSuchTokenException e) {
			Log.info("GAEBTM: No token found");
			returnToken = null;
		}
		return returnToken;
	}

	@Override
	public void init(Properties properties) {
	}

	@Override
	public boolean invalidate(String consumerKey, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Key<AccountOwner> accountOwnerKey = new Key<AccountOwner>(AccountOwner.class, (String)request.getParameter("accountOwnerHashed"));
		String serviceProvider = (String)request.getParameter("serviceProvider");
		
		OAuthTokenDAO dao = new OAuthTokenDAO();
		return dao.invalidateTokens(accountOwnerKey, serviceProvider);
	}

	@Override
	public boolean saveToken(Token token, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Key<AccountOwner> accountOwnerKey = new Key<AccountOwner>(AccountOwner.class, (String)request.getParameter("accountOwnerHashed"));
		String serviceProvider = (String)request.getParameter("serviceProvider");
		
		OAuthToken insertToken = new OAuthToken(accountOwnerKey, serviceProvider, token.getCk(), token);
		
		OAuthTokenDAO dao = new OAuthTokenDAO();
		Key result = dao.put(insertToken);

		return result != null;
	}

}
