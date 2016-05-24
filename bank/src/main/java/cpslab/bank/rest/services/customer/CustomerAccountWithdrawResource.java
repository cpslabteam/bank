package cpslab.bank.rest.services.customer;

import java.math.BigDecimal;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonPostService;

public class CustomerAccountWithdrawResource extends BaseResource implements JsonPostService {

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String amount = requestParams.getString("amount");
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			Account account = accountDAO.findCustomerAccount(getIdAttribute("customer"),
					getIdAttribute("account"));
			BigDecimal newBalance = account.getBalance().subtract(new BigDecimal(amount));
			account.setBalance(newBalance);
			Account updatedAccount = accountDAO.update(account);
			String response = EntityJsonSerializer.serialize(updatedAccount);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
