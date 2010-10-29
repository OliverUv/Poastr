package com.ollijepp.poastr.shared.socialService;

public class FacebookProvider implements ServiceProvider {

	@Override
	public String getName() {
		return "Facebook";
	}
	
	@Override
	public String infoText() {
		return "Facebook is a popular social network.";
	}

	@Override
	public SocialService getPrototype() {
		return new Facebook();
	}

	@Override
	public String getLogoPath() {
		return "facebook_logo.png";
	}

	@Override
	public Class getSocialServiceClass() {
		return Facebook.class;
	}

	@Override
	public AuthMethod getAuthMethod() {
		return AuthMethod.OAuth;
	}

}
