package cpslab.bank.rest.services.branch;

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

public class BranchAccountsResource extends BaseResource
		implements JsonGetService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		List<Account> accounts = accountDAO.findBranchAccounts(getIdAttribute("branch"));
		String response = EntityJsonSerializer.serialize(accounts);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String accountNumber = requestParams.getString("account_number");
		String balance = requestParams.getString("balance");
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		Branch branch = branchDAO.loadById(getIdAttribute("branch"));
		Account account = new Account(accountNumber, branch, new BigDecimal(balance));
		Account createdAccount = accountDAO.persist(account);
		String response = EntityJsonSerializer.serialize(createdAccount);
		getRepository().closeTransaction();
		return response;
	}
}
