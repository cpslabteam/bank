package cpslab.bank.rest.services.customer;

import java.math.BigDecimal;
import java.util.List;

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
import cpslab.util.rest.services.JsonPutService;

public class CustomerAccountsResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			List<Account> accounts = accountDAO.findCustomerAccounts(getIdAttribute("customer"));
			String response = EntityJsonSerializer.serialize(accounts);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		long accountID = requestParams.getLong("id");
		long transactionId = getRepository().openTransaction();
		try {
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			Account account = accountDAO.loadById(accountID);
			Customer customer = customerDAO.loadById(getIdAttribute("customer"));
			customer.addAccount(account);
			String response = EntityJsonSerializer.serialize(account);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String accountNumber = requestParams.getString("account_number");
		String balance = requestParams.getString("balance");
		long branchID = requestParams.getLong("branch_id");
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
			Customer customer = customerDAO.loadById(getIdAttribute("customer"));
			Branch branch = branchDAO.loadById(branchID);
			Account account = new Account(accountNumber, branch, new BigDecimal(balance));
			Account createdAccount = accountDAO.persist(account);
			customer.addAccount(createdAccount);
			String response = EntityJsonSerializer.serialize(createdAccount);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
