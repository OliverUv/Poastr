package com.ollijepp.poastr.server.datastorage.dao;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.ollijepp.poastr.server.exceptions.oauth.NoSuchTokenException;
import com.ollijepp.poastr.server.oauth.OAuthToken;
import com.ollijepp.poastr.shared.socialService.AccountOwner;

public class OAuthTokenDAO extends BaseDAO<OAuthToken> {
	public OAuthTokenDAO() {
		super(OAuthToken.class);
	}
	
	public OAuthToken getToken(Key<AccountOwner> accountOwnerKey, String serviceProvider, String consumerKey) throws NoSuchTokenException{
		Query<OAuthToken> q = ofy().query(OAuthToken.class);
		q = q.ancestor(accountOwnerKey).filter("OAuthProvider", serviceProvider).filter("consumerKey", consumerKey);
		List<OAuthToken> results = q.list();
		if (results.size() < 1){ // No results :(
			throw new NoSuchTokenException(serviceProvider, accountOwnerKey);
		} else { //this is the token you are looking for
			return q.list().get(0);
		}
	}
	
	public boolean invalidateTokens(Key<AccountOwner> accountOwnerKey, String serviceProvider){
		boolean somethingDeleted = false;
		Query<OAuthToken> q = ofy().query(OAuthToken.class);
		q = q.ancestor(accountOwnerKey).filter("OAuthProvider", serviceProvider);
		for(OAuthToken t : q){
			somethingDeleted = true;
			ofy().delete(t);
		}
		return somethingDeleted;
	}	
}
