package cpslabteam.bank.server.restapi.resources.customer;

import java.io.IOException;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.AccountDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class CustomerAccountDepositResource extends ServerResource {

	private Long customerID;
	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Post("application/json")
	public Account deposit(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String amount = request.getString("amount");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			BigDecimal newBalance = account.getBalance().add(new BigDecimal(amount));
			account.setBalance(newBalance);
			Account updatedAccount = accountDAO.update(account);
			transaction.commit();
			return updatedAccount;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
