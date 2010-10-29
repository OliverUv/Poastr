package com.ollijepp.poastr.shared.datastorage.rpc;

import java.util.List;

import com.ollijepp.poastr.shared.socialService.SocialService;

import net.customware.gwt.dispatch.shared.Result;

public class GetAccountsResult implements Result {
	public void setAccounts(List<SocialService> accounts) {
		this.accounts = accounts;
	}

	private static final long serialVersionUID = 6121710630331038939L;
	private List<SocialService> accounts;

	@SuppressWarnings("unused")
	private GetAccountsResult() {
	}

	public GetAccountsResult(List<SocialService> accounts) {
		this.accounts = accounts;
	}

	public List<SocialService> getAccounts() {
		return accounts;
	}
}
