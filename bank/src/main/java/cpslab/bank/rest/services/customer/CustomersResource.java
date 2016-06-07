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
		long transactionId = getRepository().openTransaction();
		try {
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
			List<Customer> customers = customerDAO.findAll();
			String response = EntityJsonSerializer.serialize(customers);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String customerNumber = requestParams.getString("customerNumber");
		String name = requestParams.getString("name");
		String street = requestParams.getString("street");
		String city = requestParams.getString("city");
		long transactionId = getRepository().openTransaction();
		try {
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao((Customer.class), transactionId);
			Customer customer = new Customer(customerNumber, name, street, city);
			Customer createdCustomer = customerDAO.persist(customer);
			String response = EntityJsonSerializer.serialize(createdCustomer);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
