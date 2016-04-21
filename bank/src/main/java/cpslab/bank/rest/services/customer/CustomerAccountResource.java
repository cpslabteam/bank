package cpslab.bank.rest.services.customer;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;

public class CustomerAccountResource extends ServerResource {

	private Long customerID;
	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public Account getAccount() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			tx.commit();
			return account;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeAccount() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			Customer customer = customerDAO.findById(customerID);
			customer.removeAccount(account);
			tx.commit();
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
