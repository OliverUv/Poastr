package com.ollijepp.poastr.shared.socialService;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class Twitter extends SocialService
{
	private static final long serialVersionUID = -3932068470624773017L;
	private static final String servletUrl = "twitterpoast";
	private final int lenghtLimit = 140;
	private final String logoPath = "twitter_logo.png"; 
	
	public Twitter() {
	}

	public Twitter(String accountOwnerHashed, String userNameEncrypted,
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
	public void poast(PoastBuilder poastBuilder, RequestCallback callbackHandler) throws RequestException {
		String requestUrl = GWT.getModuleBaseURL() + servletUrl;
		Log.info("Twitter: request url = " + requestUrl);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, requestUrl);
		builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		String data = poastBuilder.getHttpRequestBaseData("Twitter");
		//Use poastBuilder.addToHttpData to add service specific data
		builder.sendRequest(data, callbackHandler);
	}

	@Override
	public String getServiceProvider() {
		return "Twitter";
	}
	
	
}
