package cpslab.bank.server.restapi.resources.customer;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.database.dao.AccountDAO;
import cpslab.bank.database.dao.CustomerDAO;
import cpslab.bank.database.dao.DAOFactory;
import cpslab.bank.database.objects.Account;
import cpslab.bank.database.objects.Customer;
import cpslab.bank.database.transaction.DatabaseTransaction;
import cpslab.bank.database.transaction.DatabaseTransactionManager;

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
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			transaction.commit();
			return account;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeAccount() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			Customer customer = customerDAO.findById(customerID);
			customer.removeAccount(account);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
