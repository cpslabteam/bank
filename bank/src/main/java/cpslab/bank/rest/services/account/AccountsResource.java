package cpslab.bank.rest.services.account;

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
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Account account = new Account();
			if(requestParams.has("branch_id")){
				long branchID = requestParams.getLong("branch_id");
				account.setBranch(branchDAO.loadById(branchID));
			}
			if(requestParams.has("balance")){
				Double balance = requestParams.getDouble("balance");
				account.setBalance(balance);
			}
			if(requestParams.has("accountNumber")){
				String accountNumber = requestParams.getString("accountNumber");
				account.setAccountNumber(accountNumber);
			}
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
