package cpslab.bank.rest.services.customer;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.__DaoFactory;
import cpslab.util.db.Transaction;
import cpslab.util.db.TransactionFactory;

public class CustomersResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getCustomers() throws InterruptedException, IOException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			List<Customer> customers = customerDAO.findAll();
			tx.commit();
			return customers;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Customer createCustomer(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Transaction tx = TransactionFactory.create();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String name = request.getString("name");
			String street = request.getString("street");
			String city = request.getString("city");
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			Customer customer = new Customer(name, street, city);
			Customer createdCustomer = customerDAO.persist(customer);
			tx.commit();
			return createdCustomer;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
