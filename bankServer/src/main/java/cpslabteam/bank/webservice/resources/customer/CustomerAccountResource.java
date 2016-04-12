package cpslabteam.bank.webservice.resources.customer;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.dao.AccountDAO;
import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class CustomerAccountResource extends ServerResource {

	private Long customerID;
	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public Account getAccount() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			transaction.commit();
			return account;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeAccount() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Account account = accountDAO.findCustomerAccount(customerID, accountID);
			Customer customer = customerDAO.findById(customerID);
			customer.getAccounts().remove(account);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
