package cpslab.bank.rest.services.branch;

import java.util.List;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;

public class BranchAccountsResource extends BaseResource
		implements JsonGetService{

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			List<Account> accounts = accountDAO.findBranchAccounts(getIdAttribute("branch"));
			String response = EntityJsonSerializer.serialize(accounts);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
