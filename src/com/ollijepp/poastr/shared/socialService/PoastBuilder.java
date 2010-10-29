package com.ollijepp.poastr.shared.socialService;

import java.io.Serializable;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

public class PoastBuilder implements Serializable {
	private static final long serialVersionUID = 7891510552038019068L;
	
	private SocialService account;
	private String message;
	
	public SocialService getAccount() {
		return account;
	}
	public void setAccount(SocialService account) {
		this.account = account;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public PoastBuilder(SocialService account, String message) {
		super();
		this.account = account;
		this.message = message;
	}
	public PoastBuilder(){} //used for serialization
	
	public boolean needsShortening(){
		//TODO: conform to http://apiwiki.twitter.com/Counting-Characters
		return message.length() > account.getMessageLengthLimit();
	}
	
	/**
	 * When this poast is to be sent in an http request (built by RequestBuilder),
	 * the following data should be sent. Don't forget to do
	 * builder.setHeader("Content-Type", "application/x-www-form-urlencoded"); first.
	 * 
	 * @param The service provider used for this poast
	 * @return Data for the POST request to poasting servlet
	 */
	public String getHttpRequestBaseData(String serviceProvider){
		return "accountOwnerHashed=" + URL.encodeComponent(account.getAccountOwnerHashed())
		+ "&serviceProvider=" + URL.encodeComponent(serviceProvider)
		+ "&message=" + URL.encodeComponent(message);
	}
	
	public static String addToHttpData(String originalData, String newAttribute, String newValue){
		String result = originalData;
		if (result.length() != 0){ result += "&"; }
		return result + URL.encodeComponent(newAttribute) + "=" + URL.encodeComponent(newValue); 
	}
	
	public void poast(RequestCallback callbackHandler) throws RequestException {
		account.poast(this, callbackHandler);
	}
	
}
