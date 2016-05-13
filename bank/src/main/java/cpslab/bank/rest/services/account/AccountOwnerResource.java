package cpslab.bank.rest.services.account;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;

public class AccountOwnerResource extends BaseResource
		implements JsonGetService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer owner =
				customerDAO.findAccountOwner(getIdAttribute("account"), getIdAttribute("owner"));
		String response = EntityJsonSerializer.serialize(owner);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Account account = accountDAO.loadById(getIdAttribute("account"));
		Customer owner = customerDAO.loadById(getIdAttribute("owner"));
		boolean success = account.removeOwner(owner);
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}

}
