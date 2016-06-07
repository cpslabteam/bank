package cpslab.bank.rest.services.branch;

import java.math.BigDecimal;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class BranchAccountResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			Account account = accountDAO.findBranchAccount(getIdAttribute("branch"),
					getIdAttribute("account"));
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
			Account account = accountDAO.findBranchAccount(getIdAttribute("branch"),
					getIdAttribute("account"));
			if (requestParams.has("accountNumber"))
				account.setAccountNumber(requestParams.getString("accountNumber"));
			if (requestParams.has("balance"))
				account.setBalance(new BigDecimal(requestParams.getString("balance")));
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
			Account account = accountDAO.findBranchAccount(getIdAttribute("branch"),
					getIdAttribute("account"));
			boolean success = accountDAO.deleteById(account.getId());
			getRepository().closeTransaction(transactionId);
			return new JSONObject().put("success", success).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
