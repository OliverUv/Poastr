package com.ollijepp.poastr.server.exceptions.oauth;

import com.googlecode.objectify.Key;
import com.ollijepp.poastr.shared.socialService.AccountOwner;

public class NoSuchTokenException extends Exception {

	private static final long serialVersionUID = 5902226605448666926L;
	
	private String OAuthProvider;
	private Key<AccountOwner> accountOwnerKey;
	public String getOAuthProvider() {
		return OAuthProvider;
	}
	public Key<AccountOwner> getAccountOwnerKey() {
		return accountOwnerKey;
	}
	public NoSuchTokenException(String oAuthProvider,
			Key<AccountOwner> accountOwnerKey) {
		super();
		OAuthProvider = oAuthProvider;
		this.accountOwnerKey = accountOwnerKey;
	}

}
