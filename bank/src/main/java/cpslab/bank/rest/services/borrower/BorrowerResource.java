package cpslab.bank.rest.services.borrower;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.DAOFactory;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;

public class BorrowerResource extends ServerResource {

	private Long borrowerID;

	@Override
	protected void doInit() throws ResourceException {
		borrowerID = Long.valueOf(getAttribute("borrower"));
	}

	@Get("application/json")
	public Customer getBorrower() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.create(Customer.class);
			Customer borrower = customerDAO.findById(borrowerID);
			tx.commit();
			return borrower;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Customer borrower)
			throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			CustomerDAO borrowerDAO = (CustomerDAO) DAOFactory.create(Customer.class);
			Customer updatedCustomer = borrowerDAO.update(borrower);
			tx.commit();
			return updatedCustomer;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			CustomerDAO borrowerDAO = (CustomerDAO) DAOFactory.create(Customer.class);
			Customer borrower = borrowerDAO.findById(borrowerID);
			borrowerDAO.delete(borrower);
			tx.commit();
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}
}
