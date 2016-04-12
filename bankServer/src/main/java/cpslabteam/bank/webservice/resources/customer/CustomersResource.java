package cpslabteam.bank.webservice.resources.customer;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class CustomersResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getCustomers() throws InterruptedException, JsonProcessingException, HibernateException {
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
	public Customer createCustomer(Customer customer)
			throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
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
