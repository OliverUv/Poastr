package com.ollijepp.poastr.shared.socialService;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.DataAdapter;
import com.ollijepp.poastr.client.crypto.ClipperzCrypto;
import com.ollijepp.poastr.shared.socialService.ServiceProvider.AuthMethod;

/**
 * This factory makes available a list of service providers, which should
 * be displayed in some manner. It then builds SocialService objects (accounts)
 * using a copy of one of these providers, and the necessary user info.
 * 
 * It also makes available methods to retrieve the clear text of the user-
 * names and passwords of the accounts.
 * 
 * @author Oliver Uvman
 *
 */
@Singleton
public class SocialServiceFactory {
	
	private final DataAdapter dataAdapter;
	//private final ArrayList<ServiceProvider> serviceProviders;

	@Inject
	public SocialServiceFactory(DataAdapter dataAdapter) {
		this.dataAdapter = dataAdapter;
	}
	
	public static List<ServiceProvider> getProviders() {
		ArrayList<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
		serviceProviders.add(new FacebookProvider());
		serviceProviders.add(new TwitterProvider());
		return serviceProviders;
	}
	
	public SocialService buildSocialService(ServiceProvider serviceProvider, String accountOwner, String userName, String password){
		SocialService prototype = serviceProvider.getPrototype();
		prototype.setAccountOwnerHashed(hash(accountOwner));
		prototype.setUserNameEncrypted(encrypt(userName));
		
		if(serviceProvider.getAuthMethod() == AuthMethod.Password){
			prototype.setPasswordEncrypted(encrypt(password));
		}
		return prototype;
	}
	
	public static String getHash(String accountOwnerClearText){
		return hash(accountOwnerClearText);
	}
	
	public String getClearTextName(SocialService account){
		return decrypt(account.getUserNameEncrypted());
	}
	
	public String getClearTextPassword(SocialService account){
		return decrypt(account.getPasswordEncrypted());
	}
	
	private static String hash(String clearText){
		return ClipperzCrypto.hash(clearText);
	}
	
	private String encrypt(String clearText){
		return ClipperzCrypto.encrypt(clearText, dataAdapter.getPassPhrase());
	}
	
	private String decrypt(String cryptoText){
		return ClipperzCrypto.decrypt(cryptoText, dataAdapter.getPassPhrase());
	}
}
