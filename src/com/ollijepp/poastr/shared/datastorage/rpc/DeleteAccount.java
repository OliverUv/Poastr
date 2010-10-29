package com.ollijepp.poastr.shared.datastorage.rpc;

import com.ollijepp.poastr.shared.socialService.SocialService;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteAccount implements Action<DeleteAccountResult> {
	private static final long serialVersionUID = 5236209742355736388L;
	private SocialService account;
	
	public DeleteAccount(SocialService account) {
		super();
		this.account = account;
	}

	public SocialService getAccount() {
		return account;
	}

	public void setAccount(SocialService account) {
		this.account = account;
	}

	@SuppressWarnings("unused")
	private DeleteAccount() {
	}
}
