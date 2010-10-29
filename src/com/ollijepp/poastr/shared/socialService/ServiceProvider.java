package com.ollijepp.poastr.shared.socialService;

public interface ServiceProvider {
	public enum AuthMethod {OAuth, Password};
	public String getName();
	public String getLogoPath();
	public String infoText();
	public SocialService getPrototype();
	public Class getSocialServiceClass();
	public AuthMethod getAuthMethod();
}