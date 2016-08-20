package cpslab.bank.rest.services.account;

import java.util.List;

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
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			Account account = accountDAO.loadById(getIdAttribute("account"));
			String response = EntityJsonSerializer.serialize(account);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			Account account = accountDAO.loadById(getIdAttribute("account"));
			if(requestParams.has("accountNumber")){
				String accountNumber = requestParams.getString("accountNumber");
				List<Account> accounts = accountDAO.findByAccountNumber(accountNumber);
				if(!accounts.isEmpty())
					throw new IllegalArgumentException("Account Number must be unique");
				account.setAccountNumber(accountNumber);
			}
			if (requestParams.has("balance"))
				account.setBalance(Double.parseDouble(requestParams.getString("balance")));
			Account updatedAccount = accountDAO.update(account);
			String response = EntityJsonSerializer.serialize(updatedAccount);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			Account account = accountDAO.loadById(getIdAttribute("account"));
			boolean success = accountDAO.deleteById(account.getId());
			getRepository().closeTransaction(transactionId);
			return new JSONObject().put("success", success).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
