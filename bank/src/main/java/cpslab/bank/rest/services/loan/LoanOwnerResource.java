package cpslab.bank.rest.services.loan;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;

public class LoanOwnerResource extends ServerResource {

	private Long ownerID;
	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		ownerID = Long.valueOf(getAttribute("owner"));
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Get("application/json")
	public Customer getOwner() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Customer owner = customerDAO.findLoanOwner(loanID, ownerID);
			tx.commit();
			return owner;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeOwner() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) DAOFactory.createDao(Loan.class);
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Loan loan = loanDAO.findById(loanID);
			Customer owner = customerDAO.findById(ownerID);
			loan.removeOwner(owner);
			tx.commit();
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
