package cpslab.bank.server.restapi.resources.loan;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.database.dao.CustomerDAO;
import cpslab.bank.database.dao.DAOFactory;
import cpslab.bank.database.dao.LoanDAO;
import cpslab.bank.database.objects.Customer;
import cpslab.bank.database.objects.Loan;
import cpslab.bank.database.transaction.DatabaseTransaction;
import cpslab.bank.database.transaction.DatabaseTransactionManager;

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
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer owner = customerDAO.findLoanOwner(loanID, ownerID);
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
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Loan loan = loanDAO.findById(loanID);
			Customer owner = customerDAO.findById(ownerID);
			loan.removeOwner(owner);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
