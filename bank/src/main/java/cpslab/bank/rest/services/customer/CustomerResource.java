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
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;

public class CustomerResource extends ServerResource {

	private Long customerID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
	}

	@Get("application/json")
	public Customer getCustomer() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			tx.commit();
			return customer;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String name = request.getString("name");
			String street = request.getString("street");
			String city = request.getString("city");
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			customer.setName(name);
			customer.setStreet(street);
			customer.setCity(city);
			Customer updatedCustomer = customerDAO.update(customer);
			tx.commit();
			return updatedCustomer;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			customerDAO.delete(customer);
			tx.commit();
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}
}
