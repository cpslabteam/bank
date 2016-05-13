package cpslab.bank.rest.services.account;

import java.math.BigDecimal;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class AccountResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		Account account = accountDAO.loadById(getIdAttribute("account"));
		String response = EntityJsonSerializer.serialize(account);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		String accountNumber = requestParams.getString("account_number");
		String balance = requestParams.getString("balance");
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		Account account = accountDAO.loadById(getIdAttribute("account"));
		account.setAccountNumber(accountNumber);
		account.setBalance(new BigDecimal(balance));
		Account updatedAccount = accountDAO.update(account);
		String response = EntityJsonSerializer.serialize(updatedAccount);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		Account account = accountDAO.loadById(getIdAttribute("account"));
		boolean success = accountDAO.deleteById(account.getId());
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}
}
