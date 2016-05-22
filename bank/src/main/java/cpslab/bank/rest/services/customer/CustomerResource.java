package cpslab.bank.rest.services.customer;

import org.json.JSONObject;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class CustomerResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		String response = EntityJsonSerializer.serialize(customer);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		if (requestParams.has("name"))
			customer.setName(requestParams.getString("name"));
		if (requestParams.has("street"))
			customer.setStreet(requestParams.getString("street"));
		if (requestParams.has("city"))
			customer.setCity(requestParams.getString("city"));
		Customer updatedCustomer = customerDAO.update(customer);
		String response = EntityJsonSerializer.serialize(updatedCustomer);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		boolean success = customerDAO.deleteById(customer.getId());
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}

}
