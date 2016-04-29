package cpslab.bank.rest.services.account;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

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
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer owner = customerDAO.findAccountOwner(accountID, ownerID);
			r.closeTransaction();
			return owner;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Delete("application/json")
	public void removeOwner() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Account account = accountDAO.findById(accountID);
			Customer owner = customerDAO.findById(ownerID);
			account.removeOwner(owner);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

}
