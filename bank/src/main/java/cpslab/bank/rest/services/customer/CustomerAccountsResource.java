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
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		List<Account> accounts = accountDAO.findCustomerAccounts(getIdAttribute("customer"));
		String response = EntityJsonSerializer.serialize(accounts);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		long accountID = requestParams.getLong("id");
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		Account account = accountDAO.loadById(accountID);
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		customer.addAccount(account);
		String response = EntityJsonSerializer.serialize(account);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		String accountNumber = requestParams.getString("account_number");
		String balance = requestParams.getString("balance");
		long branchID = requestParams.getLong("branch_id");
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		Branch branch = branchDAO.loadById(branchID);
		Account account = new Account(accountNumber, branch, new BigDecimal(balance));
		Account createdAccount = accountDAO.persist(account);
		customer.addAccount(createdAccount);
		String response = EntityJsonSerializer.serialize(createdAccount);
		getRepository().closeTransaction();
		return response;
	}
}
