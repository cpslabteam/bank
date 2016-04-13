package cpslabteam.bank.webservice.resources.customer;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class CustomersResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getCustomers() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			List<Customer> customers = customerDAO.findAll();
			transaction.commit();
			return customers;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Customer createCustomer(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String name = request.getString("name");
			String street = request.getString("street");
			String city = request.getString("city");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer customer = new Customer(name, street, city);
			Customer createdCustomer = customerDAO.persist(customer);
			transaction.commit();
			return createdCustomer;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
