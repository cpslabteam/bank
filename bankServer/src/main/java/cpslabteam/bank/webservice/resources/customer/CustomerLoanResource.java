package cpslabteam.bank.webservice.resources.customer;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.LoanDAO;
import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Loan;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

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
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findCustomerLoan(customerID, loanID);
			transaction.commit();
			return loan;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void removeLoan() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Loan loan = loanDAO.findCustomerLoan(customerID, loanID);
			Customer customer = customerDAO.findById(customerID);
			customer.removeLoan(loan);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}


}
