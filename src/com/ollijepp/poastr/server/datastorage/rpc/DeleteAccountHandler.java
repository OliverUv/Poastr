package com.ollijepp.poastr.server.datastorage.rpc;

import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.googlecode.objectify.Key;
import com.ollijepp.poastr.server.datastorage.dao.OAuthTokenDAO;
import com.ollijepp.poastr.server.datastorage.dao.SocialServiceDAO;
import com.ollijepp.poastr.server.oauth.OAuthToken;
import com.ollijepp.poastr.shared.datastorage.rpc.AddAccount;
import com.ollijepp.poastr.shared.datastorage.rpc.AddAccountResult;
import com.ollijepp.poastr.shared.datastorage.rpc.DeleteAccount;
import com.ollijepp.poastr.shared.datastorage.rpc.DeleteAccountResult;
import com.ollijepp.poastr.shared.socialService.SocialService;


public class DeleteAccountHandler implements ActionHandler<DeleteAccount, DeleteAccountResult> {
	
	private boolean accountDeleted; //for rollback
	
	@Override
	public DeleteAccountResult execute(DeleteAccount action, ExecutionContext context) throws ActionException {
		accountDeleted = false;
		SocialService account = action.getAccount();
		SocialServiceDAO ssDao = new SocialServiceDAO();
		ssDao.delete(account);
		accountDeleted = true;
		OAuthTokenDAO oDao = new OAuthTokenDAO();
		oDao.invalidateTokens(
				account.getAccountOwnerKey(),
				account.getServiceProvider());
		
		return new DeleteAccountResult(account);
	}

	@Override
	public Class<DeleteAccount> getActionType() {
		return DeleteAccount.class;
	}

	@Override
	public void rollback(DeleteAccount action, DeleteAccountResult result, ExecutionContext context) throws ActionException {
		if (accountDeleted){
			SocialServiceDAO dao = new SocialServiceDAO();
			dao.put(action.getAccount());
		}
	}
}
