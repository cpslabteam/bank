package cpslab.bank.rest.services.account;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class AccountBranchResource extends BaseResource implements JsonGetService, JsonPutService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao((Account.class), transactionId);
			Account account = accountDAO.loadById(getIdAttribute("account"));
			Branch branch = account.getBranch();
			String response = EntityJsonSerializer.serialize(branch);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		long branchID = requestParams.getLong("id");
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao((Account.class), transactionId);
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao((Branch.class), transactionId);
			Account account = accountDAO.loadById(getIdAttribute("account"));
			Branch branch = branchDAO.loadById(branchID);
			account.setBranch(branch);
			accountDAO.update(account);
			String response = EntityJsonSerializer.serialize(branch);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
