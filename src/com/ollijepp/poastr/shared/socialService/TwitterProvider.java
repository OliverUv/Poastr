package com.ollijepp.poastr.shared.socialService;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.URL;

public class TwitterProvider implements ServiceProvider {

	@Override
	public String getName() {
		return "Twitter";
	}
	
	@Override
	public String infoText() {
		return "Twitter is a popular microblogging service.";
	}

	@Override
	public SocialService getPrototype() {
		return new Twitter();
	}

	@Override
	public String getLogoPath() {
		return "twitter_logo.png";
	}

	@Override
	public Class getSocialServiceClass() {
		return Twitter.class;
	}

	@Override
	public AuthMethod getAuthMethod() {
		return AuthMethod.OAuth;
	}
}
