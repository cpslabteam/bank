package cpslab.bank.rest.services.loan;

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

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.__DaoFactory;
import cpslab.util.db.Transaction;
import cpslab.util.db.TransactionFactory;

public class LoanOwnersResource extends ServerResource {

	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Get("application/json")
	public List<Customer> getOwners() throws InterruptedException, IOException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			List<Customer> owners = customerDAO.findLoanOwners(loanID);
			tx.commit();
			return owners;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Customer addOwner(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Transaction tx = TransactionFactory.create();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String ownerID = request.getString("id");
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) __DaoFactory.create(Loan.class);
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			Customer owner = customerDAO.findById(Long.valueOf(ownerID));
			Loan loan = loanDAO.findById(loanID);
			loan.addOwner(owner);
			tx.commit();
			return owner;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
