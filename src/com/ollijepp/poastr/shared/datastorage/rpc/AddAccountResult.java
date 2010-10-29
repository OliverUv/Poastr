package com.ollijepp.poastr.shared.datastorage.rpc;

import java.util.List;

import com.ollijepp.poastr.shared.socialService.SocialService;

import net.customware.gwt.dispatch.shared.Result;

public class AddAccountResult implements Result {
	private static final long serialVersionUID = -753634760449035166L;
	private SocialService account;

	@SuppressWarnings("unused")
	private AddAccountResult() {
	}

	public AddAccountResult(SocialService account) {
		this.account = account;
	}

	public SocialService getAccount() {
		return account;
	}

	public void setAccount(SocialService account) {
		this.account = account;
	}
}
