package com.ollijepp.poastr.shared.datastorage.rpc;

import java.util.List;

import com.ollijepp.poastr.shared.socialService.SocialService;

import net.customware.gwt.dispatch.shared.Result;

public class DeleteAccountResult implements Result {
	private static final long serialVersionUID = 1383058738137898350L;
	private SocialService account;

	@SuppressWarnings("unused")
	private DeleteAccountResult() {
	}

	public DeleteAccountResult(SocialService account) {
		this.account = account;
	}

	public SocialService getAccount() {
		return account;
	}

	public void setAccount(SocialService account) {
		this.account = account;
	}
}
