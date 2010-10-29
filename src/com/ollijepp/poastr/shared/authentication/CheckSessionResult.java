package com.ollijepp.poastr.shared.authentication;

import net.customware.gwt.dispatch.shared.Result;

public class CheckSessionResult implements Result {
	private static final long serialVersionUID = -3846487880935642023L;
	boolean valid = false;
	private String userOpenId;

	public CheckSessionResult() {
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean getValid(){
		return valid;
	}
	
	public boolean isValid() {
		return valid; // && user.getAuthenticated();
	}
}
