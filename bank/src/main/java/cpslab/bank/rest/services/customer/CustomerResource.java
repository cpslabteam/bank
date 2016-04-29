package cpslab.bank.rest.services.customer;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class CustomerResource extends ServerResource {

	private Long customerID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
	}

	@Get("application/json")
	public Customer getCustomer() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			r.closeTransaction();
			return customer;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String name = request.getString("name");
			String street = request.getString("street");
			String city = request.getString("city");
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			customer.setName(name);
			customer.setStreet(street);
			customer.setCity(city);
			Customer updatedCustomer = customerDAO.update(customer);
			r.closeTransaction();
			return updatedCustomer;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			customerDAO.delete(customer);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
