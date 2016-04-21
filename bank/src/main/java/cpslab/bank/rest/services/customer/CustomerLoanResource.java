package cpslab.bank.rest.services.customer;

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

public class CustomerLoanResource extends ServerResource {

	private Long customerID;
	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Get("application/json")
	public Loan getLoan() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) DAOFactory.createDao(Loan.class);
			Loan loan = loanDAO.findCustomerLoan(customerID, loanID);
			tx.commit();
			return loan;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeLoan() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) DAOFactory.createDao(Loan.class);
			CustomerDAO customerDAO = (CustomerDAO) DAOFactory.createDao(Customer.class);
			Loan loan = loanDAO.findCustomerLoan(customerID, loanID);
			Customer customer = customerDAO.findById(customerID);
			customer.removeLoan(loan);
			tx.commit();
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}


}
