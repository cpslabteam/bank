package cpslab.bank.rest.services.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class AccountResource extends ServerResource {

	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public Account getAccount() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
			Account account = accountDAO.findById(accountID);
			r.closeTransaction();
			return account;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Put("application/json")
	public Account updateAccount(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String accountNumber = request.getString("account_number");
			String balance = request.getString("balance");
			r.openTransaction();
			
			AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
			Account account = accountDAO.findById(accountID);
			account.setAccountNumber(accountNumber);
			account.setBalance(new BigDecimal(balance));
			Account updatedAccount = accountDAO.update(account);
			r.closeTransaction();
			return updatedAccount;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Delete("application/json")
	public void deleteAccount() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
			Account account = accountDAO.findById(accountID);
			accountDAO.delete(account);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
