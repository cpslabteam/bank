package cpslabteam.bank.webservice.resources;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.AccountDAO;
import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.database.objects.Customer;

public class CustomerAccountsResource extends ServerResource {

	private String customerID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = getAttribute("customer");
	}

	@Get("application/json")
	public List<Account> getAccounts() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			List<Account> accounts = accountDAO.findCustomerAccounts(Long.valueOf(customerID));
			SessionManager.getSession().getTransaction().commit();
			return accounts;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}

	@Post("application/json")
	public List<Account> addAccount(Account account)
			throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer customer = customerDAO.findById(Long.valueOf(customerID));
			customer.getAccounts().add(account);
			List<Account> accounts = daoFactory.getAccountDAO().findCustomerAccounts(Long.valueOf(customerID));
			SessionManager.getSession().getTransaction().commit();
			return accounts;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}

}
