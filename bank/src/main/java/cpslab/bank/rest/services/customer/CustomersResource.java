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
		long transactionId = getRepository().openTransaction();
		try {
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao((Customer.class), transactionId);
			Customer customer = new Customer();
			if(requestParams.has("customerNumber")){
				String customerNumber = requestParams.getString("customerNumber");
				customer.setCustomerNumber(customerNumber);
			}
			if(requestParams.has("name")){
				String name = requestParams.getString("name");
				customer.setName(name);
			}
			if(requestParams.has("street")){
				String street = requestParams.getString("street");
				customer.setStreet(street);
			}
			if(requestParams.has("city")){
				String city = requestParams.getString("city");
				customer.setCity(city);
			}
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
