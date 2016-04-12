package cpslabteam.bank.webservice.resources.account;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.dao.AccountDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class AccountResource extends ServerResource {

	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public Account getAccount() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account account = accountDAO.findById(accountID);
			transaction.commit();
			return account;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Account updateAccount(Account account)
			throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account updatedAccount = accountDAO.update(account);
			transaction.commit();
			return updatedAccount;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteAccount() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account account = accountDAO.findById(accountID);
			accountDAO.delete(account);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
