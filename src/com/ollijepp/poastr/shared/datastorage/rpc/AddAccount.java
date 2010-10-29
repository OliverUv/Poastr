package com.ollijepp.poastr.shared.datastorage.rpc;

import com.ollijepp.poastr.shared.socialService.SocialService;

import net.customware.gwt.dispatch.shared.Action;

public class AddAccount implements Action<AddAccountResult> {
	private static final long serialVersionUID = 7407123484315532054L;
	private SocialService account;
	
	public AddAccount(SocialService account) {
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
	private AddAccount() {
	}
}
