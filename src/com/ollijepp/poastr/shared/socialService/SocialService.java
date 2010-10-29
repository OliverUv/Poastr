package com.ollijepp.poastr.shared.socialService;

import java.io.Serializable;

import javax.persistence.Id;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.ollijepp.poastr.shared.exceptions.socialService.PoastTooLongException;

@Cached @Unindexed //save space & time (money) in GAE by only indexing neccessities
public abstract class SocialService implements Serializable 
{
	private static final long serialVersionUID = -1057313385853430468L;
	
	@Id Long id; //For datastorage persistence
	
	@Parent protected Key<AccountOwner> accountOwnerKey;
	protected String serviceProvider;
	protected String accountOwnerHashed; //so we can find it in datastore
	
	public abstract String getServiceProvider(); //used to delete oauth tokens from database

	protected String userNameEncrypted; //place to post to
	protected String passwordEncrypted; //password to use, if any
	
	public SocialService(String accountOwnerHashed, String userNameEncrypted,
			String passwordEncrypted) {
		this.accountOwnerHashed = accountOwnerHashed;
		this.accountOwnerKey = new Key<AccountOwner>(AccountOwner.class, accountOwnerHashed);
		this.userNameEncrypted = userNameEncrypted;
		this.passwordEncrypted = passwordEncrypted;
	}

	public SocialService(){} //necessary for serialization

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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
	
	// getLogoPath - e.g. facebookLogo.png
	public abstract String getLogoPath();
	
	public String getUserNameEncrypted() {
		return userNameEncrypted;
	}

	public String getPasswordEncrypted() {
		return passwordEncrypted;
	}

	// getMessageLengthLimit
	public abstract int getMessageLengthLimit();
	
	/**
	 * This method should return a poast builder which specifies whether any
	 * changes need to be done (translations checked, messages shortened, etc).
	 * The builders are collected by some presenter and sent as an event, which
	 * 
	 * 
	 * @param message
	 * @throws PoastTooLongException
	 */
	public abstract PoastBuilder buildPoast(String message);
	
	//Poasts a poast
	public abstract void poast(PoastBuilder poastBuilder, RequestCallback callbackHandler) throws RequestException;

	public String getAccountOwnerHashed() {
		return accountOwnerHashed;
	}

	public void setAccountOwnerHashed(String accountOwnerHashed) {
		this.accountOwnerHashed = accountOwnerHashed;
		this.accountOwnerKey = new Key<AccountOwner>(AccountOwner.class, accountOwnerHashed);
	}

	public void setUserNameEncrypted(String userNameEncrypted) {
		this.userNameEncrypted = userNameEncrypted;
	}

	public void setPasswordEncrypted(String passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}
}
