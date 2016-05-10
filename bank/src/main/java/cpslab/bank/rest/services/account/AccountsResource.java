package cpslab.bank.rest.services.account;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.jsonserialization.BankJsonSerializer;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;
import cpslab.util.rest.RestJSONServicesProvider;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;

public class AccountsResource extends RestJSONServicesProvider
		implements JsonGetService, JsonPostService {

	private Repository repository = RepositoryService.getInstance();

	@Override
	public String handleGet() throws Throwable {
		repository.openTransaction();
		AccountDAO accountDAO = (AccountDAO) repository.createDao(Account.class);
		List<Account> accounts = accountDAO.findAll();
		String response = BankJsonSerializer.serialize(accounts);
		repository.closeTransaction();
		return response;
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String accountNumber = requestParams.getString("account_number");
		String balance = requestParams.getString("balance");
		String branchID = requestParams.getString("branch_id");
		repository.openTransaction();
		AccountDAO accountDAO = (AccountDAO) repository.createDao(Account.class);
		BranchDAO branchDAO = (BranchDAO) repository.createDao(Branch.class);
		Branch branch = branchDAO.findById(Long.valueOf(branchID));
		Account account = new Account(accountNumber, branch, new BigDecimal(balance));
		Account createdAccount = accountDAO.persist(account);
		String response = BankJsonSerializer.serialize(createdAccount);
		repository.closeTransaction();
		return response;
	}

}
