package com.ollijepp.poastr.server.datastorage.rpc;

import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.inject.Inject;
import com.googlecode.objectify.Key;

import com.ollijepp.poastr.server.datastorage.dao.SocialServiceDAO;
import com.ollijepp.poastr.shared.datastorage.rpc.GetAccounts;
import com.ollijepp.poastr.shared.datastorage.rpc.GetAccountsResult;
import com.ollijepp.poastr.shared.socialService.AccountOwner;
import com.ollijepp.poastr.shared.socialService.SocialService;

public class GetAccountsHandler implements ActionHandler<GetAccounts, GetAccountsResult> {
	private static final Logger log = Logger.getLogger(GetAccountsHandler.class);
	
	@Override
	public GetAccountsResult execute(GetAccounts action, ExecutionContext context) throws ActionException {
		SocialServiceDAO dao = new SocialServiceDAO();
		List<SocialService> resultList = null;
		resultList = dao.getAccounts(action.getAccountOwnerKey());
		String logText = "GetAccountsHandler: Got accounts for " + action.getAccountOwnerKey() + ": ";
		for(SocialService s : resultList){
			logText += s + ", ";
		}
		log.info(logText);
		return new GetAccountsResult(resultList);
	}

	@Override
	public Class<GetAccounts> getActionType() {
		return GetAccounts.class;
	}

	@Override
	public void rollback(GetAccounts action, GetAccountsResult result, ExecutionContext context) throws ActionException {
		// Nothing to do here
	}
}
