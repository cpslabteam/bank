package cpslab.bank.rest.services.customer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;

public class CustomerAccountsResource extends ServerResource {

	private Long customerID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
	}

	@Get("application/json")
	public List<Account> getAccounts() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
			List<Account> accounts = accountDAO.findCustomerAccounts(customerID);
			tx.commit();
			return accounts;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Account addAccount(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String accountID = request.getString("id");
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
			Account account = accountDAO.findById(Long.valueOf(accountID));
			Customer customer = customerDAO.findById(customerID);
			customer.addAccount(account);
			tx.commit();
			return account;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Account createAccount(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String accountNumber = request.getString("account_number");
			String balance = request.getString("balance");
			String branchID = request.getString("branch_id");
			tx.begin();
			
			AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
			BranchDAO branchDAO = (BranchDAO) DAOFactory.createDao(Branch.class);
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			Account account = new Account(accountNumber, branch, new BigDecimal(balance));
			Account createdAccount = accountDAO.persist(account);
			customer.addAccount(createdAccount);
			tx.commit();
			return createdAccount;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
