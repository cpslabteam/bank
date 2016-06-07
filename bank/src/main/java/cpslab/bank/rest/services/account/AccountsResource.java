package cpslab.bank.rest.services.account;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;

public class AccountsResource extends BaseResource implements JsonGetService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			List<Account> accounts = accountDAO.findAll();
			String response = EntityJsonSerializer.serialize(accounts);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String accountNumber = requestParams.getString("accountNumber");
		String balance = requestParams.getString("balance");
		long branchID = requestParams.getLong("branch_id");
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Branch branch = branchDAO.loadById(branchID);
			Account account = new Account(accountNumber, branch, new BigDecimal(balance));
			Account createdAccount = accountDAO.persist(account);
			String response = EntityJsonSerializer.serialize(createdAccount);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
