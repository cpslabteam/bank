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
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class AccountOwnersResource extends ServerResource {

	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public List<Customer> getOwners() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			List<Customer> owners = customerDAO.findAccountOwners(accountID);
			r.closeTransaction();
			return owners;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Put("application/json")
	public Customer addOwner(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String ownerID = request.getString("id");
			r.openTransaction();
			
			AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer owner = customerDAO.findById(Long.valueOf(ownerID));
			Account account = accountDAO.findById(accountID);
			account.addOwner(owner);
			r.closeTransaction();
			return owner;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

}
