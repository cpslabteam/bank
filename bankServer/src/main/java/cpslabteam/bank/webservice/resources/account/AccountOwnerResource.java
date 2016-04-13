package cpslabteam.bank.webservice.resources.account;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.AccountDAO;
import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

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
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer owner = customerDAO.findAccountOwner(accountID, ownerID);
			transaction.commit();
			return owner;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeOwner() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Account account = accountDAO.findById(accountID);
			Customer owner = customerDAO.findById(ownerID);
			account.removeOwner(owner);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
