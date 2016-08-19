package cpslab.bank.rest.services.account;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;

public class AccountOwnerResource extends BaseResource
		implements JsonDeleteService {

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
			Account account = accountDAO.loadById(getIdAttribute("account"));
			Customer owner = customerDAO.loadById(getIdAttribute("owner"));
			boolean success = account.removeOwner(owner);
			getRepository().closeTransaction(transactionId);
			return new JSONObject().put("success", success).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
