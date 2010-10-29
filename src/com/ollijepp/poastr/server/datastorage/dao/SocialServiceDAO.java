package com.ollijepp.poastr.server.datastorage.dao;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.ollijepp.poastr.shared.socialService.AccountOwner;
import com.ollijepp.poastr.shared.socialService.Facebook;
import com.ollijepp.poastr.shared.socialService.ServiceProvider;
import com.ollijepp.poastr.shared.socialService.SocialService;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;

public class SocialServiceDAO extends BaseDAO<SocialService> {
	public SocialServiceDAO() {
		super(SocialService.class);
	}
	
	/**
	 * This function queries GAE once for each type of service provider available,
	 * fetching any accounts stored for the account owner. We can't just
	 * query for all SocialService objects because GAE/objectify doesn't support
	 * polymorphic queries.
	 * 
	 * @param accountOwnerKey Key of the account owner
	 * @return A list of all SocialService objects belonging to the account owner
	 */
	public List<SocialService> getAccounts(Key<AccountOwner> accountOwnerKey){
		ArrayList<SocialService> accounts = new ArrayList<SocialService>();
		for(ServiceProvider sp : SocialServiceFactory.getProviders()){
			Query q = ofy().query(sp.getSocialServiceClass());
			q = q.ancestor(accountOwnerKey);
			accounts.addAll(q.list());
		}
		return accounts;
	}

	@Override
	public Key<SocialService> put(SocialService entity) {
		return super.put(entity);
	}
}