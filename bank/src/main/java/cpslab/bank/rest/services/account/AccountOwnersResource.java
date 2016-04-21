package cpslab.bank.rest.services.account;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.util.db.DatabaseTransaction;
import cpslab.bank.util.db.DatabaseTransactionManager;

public class AccountOwnersResource extends ServerResource {

	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public List<Customer> getOwners() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			List<Customer> owners = customerDAO.findAccountOwners(accountID);
			transaction.commit();
			return owners;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Customer addOwner(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String ownerID = request.getString("id");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer owner = customerDAO.findById(Long.valueOf(ownerID));
			Account account = accountDAO.findById(accountID);
			account.addOwner(owner);
			transaction.commit();
			return owner;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
