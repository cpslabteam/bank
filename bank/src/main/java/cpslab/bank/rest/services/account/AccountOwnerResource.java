package cpslab.bank.rest.services.account;

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

public class AccountOwnerResource extends ServerResource {

	private Long ownerID;
	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		ownerID = Long.valueOf(getAttribute("owner"));
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public Customer getOwner() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Customer owner = customerDAO.findAccountOwner(accountID, ownerID);
			tx.commit();
			return owner;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeOwner() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Account account = accountDAO.findById(accountID);
			Customer owner = customerDAO.findById(ownerID);
			account.removeOwner(owner);
			tx.commit();
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
