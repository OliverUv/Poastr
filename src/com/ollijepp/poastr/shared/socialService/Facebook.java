package com.ollijepp.poastr.shared.socialService;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class Facebook extends SocialService
{
	private static final long serialVersionUID = 6023418156251068751L;
	private final int lenghtLimit = 420;
	private final String logoPath = "facebook_logo.png"; 
	
	public Facebook() {
		super();
	}

	

	public Facebook(String accountOwnerHashed, String userNameEncrypted,
			String passwordEncrypted) {
		super(accountOwnerHashed, userNameEncrypted, passwordEncrypted);
	}



	@Override
	public String getLogoPath()
	{
		return logoPath;
	}

	@Override
	public int getMessageLengthLimit()
	{
		return lenghtLimit;
	}

	@Override
	public PoastBuilder buildPoast(String message) {
		return new PoastBuilder(this, message);
	}

	@Override
	public void poast(PoastBuilder poastBuilder, RequestCallback callbackHandler)
			throws RequestException {
		return;
	}



	@Override
	public String getServiceProvider() {
		return "Facebook";
	}
	
	
}
