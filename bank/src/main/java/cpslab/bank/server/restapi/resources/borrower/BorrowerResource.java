package cpslab.bank.server.restapi.resources.borrower;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.database.dao.CustomerDAO;
import cpslab.bank.database.dao.DAOFactory;
import cpslab.bank.database.objects.Customer;
import cpslab.bank.database.transaction.DatabaseTransaction;
import cpslab.bank.database.transaction.DatabaseTransactionManager;

public class BorrowerResource extends ServerResource {

	private Long borrowerID;

	@Override
	protected void doInit() throws ResourceException {
		borrowerID = Long.valueOf(getAttribute("borrower"));
	}

	@Get("application/json")
	public Customer getBorrower() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer borrower = customerDAO.findById(borrowerID);
			transaction.commit();
			return borrower;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Customer borrower)
			throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO borrowerDAO = daoFactory.getCustomerDAO();
			Customer updatedCustomer = borrowerDAO.update(borrower);
			transaction.commit();
			return updatedCustomer;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO borrowerDAO = daoFactory.getCustomerDAO();
			Customer borrower = borrowerDAO.findById(borrowerID);
			borrowerDAO.delete(borrower);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
