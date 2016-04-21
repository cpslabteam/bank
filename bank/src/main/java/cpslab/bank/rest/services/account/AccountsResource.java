package cpslab.bank.rest.services.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;

public class AccountsResource extends ServerResource {

	@Get("application/json")
	public List<Account> getAccounts() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
			List<Account> accounts = accountDAO.findAll();
			tx.commit();			
			return accounts;
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
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			Account account = new Account(accountNumber, branch, new BigDecimal(balance));
			Account createdAccount = accountDAO.persist(account);
			tx.commit();
			return createdAccount;
		} catch (Exception e) {
			System.out.println(e);
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
