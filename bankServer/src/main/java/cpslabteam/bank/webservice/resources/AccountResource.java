package cpslabteam.bank.webservice.resources;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.AccountDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

public class AccountResource extends ServerResource {

	private String accountID;

	@Override
	protected void doInit() throws ResourceException {
		accountID = getAttribute("account");
	}

	@Get("application/json")
	public String doGet() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account account = accountDAO.findById(Long.valueOf(accountID));
			String response = BankJsonSerializer.serializeDetails(account);
			SessionManager.getSession().getTransaction().commit();
			return response;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}
