package cpslab.bank.rest.services.account;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Customer;
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
				List<Account> accounts = accountDAO.findByAccountNumber(accountNumber);
				if(!accounts.isEmpty())
					throw new IllegalArgumentException("Account Number must be unique");
				account.setAccountNumber(accountNumber);
			}
			Account createdAccount = accountDAO.persist(account);
			if(requestParams.has("owners")){
				CustomerDAO customerDAO =
						(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
				JSONArray owners = requestParams.getJSONArray("owners");
				for (int i = 0; i < owners.length(); i++) {
					long ownerId = owners.getJSONObject(i).getLong("id");
					Customer owner = customerDAO.loadById(ownerId);
					account.addOwner(owner);
				}
			}
			String response = EntityJsonSerializer.serialize(createdAccount);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
