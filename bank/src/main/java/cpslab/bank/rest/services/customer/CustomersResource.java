package cpslab.bank.rest.services.customer;

import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;

public class CustomersResource extends BaseResource implements JsonGetService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		List<Customer> customers = customerDAO.findAll();
		String response = EntityJsonSerializer.serialize(customers);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String name = requestParams.getString("name");
		String street = requestParams.getString("street");
		String city = requestParams.getString("city");
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer customer = new Customer(name, street, city);
		Customer createdCustomer = customerDAO.persist(customer);
		String response = EntityJsonSerializer.serialize(createdCustomer);
		getRepository().closeTransaction();
		return response;
	}
}
