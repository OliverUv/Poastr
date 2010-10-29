package com.ollijepp.poastr.server.datastorage.rpc;

import java.util.List;

import org.apache.log4j.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.googlecode.objectify.Key;
import com.ollijepp.poastr.server.datastorage.dao.SocialServiceDAO;
import com.ollijepp.poastr.shared.datastorage.rpc.AddAccount;
import com.ollijepp.poastr.shared.datastorage.rpc.AddAccountResult;
import com.ollijepp.poastr.shared.socialService.SocialService;

public class AddAccountHandler implements ActionHandler<AddAccount, AddAccountResult> {
	private static final Logger log = Logger.getLogger(AddAccountHandler.class);
	
	@Override
	public AddAccountResult execute(AddAccount action, ExecutionContext context) throws ActionException {
		SocialServiceDAO dao = new SocialServiceDAO();
		dao.put(action.getAccount());
		log.info("MakePoastHandler: added " + action.getAccount().getUserNameEncrypted() + " for " + action.getAccount().getAccountOwnerKey());
		return new AddAccountResult(action.getAccount());
	}

	@Override
	public Class<AddAccount> getActionType() {
		return AddAccount.class;
	}

	@Override
	public void rollback(AddAccount action, AddAccountResult result, ExecutionContext context) throws ActionException {
		// Nothing to do here
	}
}
