package com.ollijepp.poastr.server.oauth;

import java.io.Serializable;

import javax.persistence.Id;

import com.dyuproject.oauth.Token;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialized;
import com.googlecode.objectify.annotation.Unindexed;
import com.ollijepp.poastr.shared.socialService.AccountOwner;

@Cached @Unindexed
public class OAuthToken implements Serializable {
	private static final long serialVersionUID = 1101142375824139855L;
	@Id Long id; //For GAE persistence
	
	@Parent private Key<AccountOwner> accountOwnerKey;
	@Indexed private String OAuthProvider;
	@Indexed private String consumerKey;
	
	@Serialized private Token OAuthToken;
	
	public OAuthToken() {}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Key<AccountOwner> getAccountOwnerKey() {
		return accountOwnerKey;
	}



	public void setAccountOwnerKey(Key<AccountOwner> accountOwnerKey) {
		this.accountOwnerKey = accountOwnerKey;
	}



	public String getOAuthProvider() {
		return OAuthProvider;
	}



	public void setOAuthProvider(String oAuthProvider) {
		OAuthProvider = oAuthProvider;
	}



	public String getConsumerKey() {
		return consumerKey;
	}



	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}



	public Token getOAuthToken() {
		return OAuthToken;
	}



	public void setOAuthToken(Token oAuthToken) {
		OAuthToken = oAuthToken;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public OAuthToken(Key<AccountOwner> accountOwnerKey,
			String oAuthProvider, String consumerKey, Token oAuthToken) {
		super();
		this.accountOwnerKey = accountOwnerKey;
		OAuthProvider = oAuthProvider;
		this.consumerKey = consumerKey;
		OAuthToken = oAuthToken;
	}
	
	
}
