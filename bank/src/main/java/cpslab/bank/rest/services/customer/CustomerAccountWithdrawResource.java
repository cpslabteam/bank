package cpslab.bank.rest.services.customer;

import java.io.IOException;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.util.db.__DaoFactory;
import cpslab.util.db.Transaction;
import cpslab.util.db.TransactionFactory;

public class CustomerAccountWithdrawResource extends ServerResource {

	private Long customerID;
	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Post("application/json")
	public Account withdraw(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Transaction tx = TransactionFactory.create();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String amount = request.getString("amount");
			tx.begin();
			
			AccountDAO accountDAO = (AccountDAO) __DaoFactory.create(Account.class);
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			BigDecimal newBalance = account.getBalance().subtract(new BigDecimal(amount));
			account.setBalance(newBalance);
			Account updatedAccount = accountDAO.update(account);
			tx.commit();
			return updatedAccount;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}
}
