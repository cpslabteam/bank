package cpslab.bank.rest.services.customer;

import org.json.JSONObject;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;

public class CustomerAccountResource extends BaseResource
		implements JsonGetService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		Account account = accountDAO.findCustomerAccount(getIdAttribute("customer"), getIdAttribute("account"));
		String response = EntityJsonSerializer.serialize(account);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		AccountDAO accountDAO = (AccountDAO) getRepository().createDao(Account.class);
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Account account = accountDAO.findCustomerAccount(getIdAttribute("customer"), getIdAttribute("account"));
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		boolean success = customer.removeAccount(account);
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}
}
