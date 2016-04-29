package cpslab.bank.rest.services.customer;

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
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			r.closeTransaction();
			return account;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Delete("application/json")
	public void removeAccount() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			Customer customer = customerDAO.findById(customerID);
			customer.removeAccount(account);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

}
