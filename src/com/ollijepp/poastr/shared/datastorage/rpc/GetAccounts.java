package com.ollijepp.poastr.shared.datastorage.rpc;

import com.googlecode.objectify.Key;
import com.ollijepp.poastr.shared.socialService.AccountOwner;

import net.customware.gwt.dispatch.shared.Action;

public class GetAccounts implements Action<GetAccountsResult> {
	private static final long serialVersionUID = -3473835880799058358L;
	private Key<AccountOwner> accountOwnerKey;
	
	public GetAccounts(Key<AccountOwner> accountOwnerKey) {
		super();
		this.accountOwnerKey = accountOwnerKey;
	}

	public Key<AccountOwner> getAccountOwnerKey() {
		return accountOwnerKey;
	}

	public void setAccountOwnerKey(Key<AccountOwner> accountOwnerHashed) {
		this.accountOwnerKey = accountOwnerHashed;
	}

	@SuppressWarnings("unused")
	private GetAccounts() {
	}
}
