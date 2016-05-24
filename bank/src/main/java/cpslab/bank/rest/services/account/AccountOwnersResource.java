package cpslab.bank.rest.services.account;

import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class AccountOwnersResource extends BaseResource implements JsonGetService, JsonPutService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
			List<Customer> owners = customerDAO.findAccountOwners(getIdAttribute("account"));
			String response = EntityJsonSerializer.serialize(owners);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		long ownerID = requestParams.getLong("id");
		long transactionId = getRepository().openTransaction();
		try {
			AccountDAO accountDAO =
					(AccountDAO) getRepository().createDao(Account.class, transactionId);
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
			Customer owner = customerDAO.loadById(ownerID);
			Account account = accountDAO.loadById(getIdAttribute("account"));
			account.addOwner(owner);
			String response = EntityJsonSerializer.serialize(owner);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
