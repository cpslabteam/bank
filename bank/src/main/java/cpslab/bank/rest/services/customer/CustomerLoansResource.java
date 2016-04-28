package cpslab.bank.rest.services.customer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.__DaoFactory;
import cpslab.util.db.Transaction;
import cpslab.util.db.TransactionFactory;

public class CustomerLoansResource extends ServerResource {

	private Long customerID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
	}

	@Get("application/json")
	public List<Loan> getLoans() throws InterruptedException, IOException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) __DaoFactory.create(Loan.class);
			List<Loan> loans = loanDAO.findCustomerLoans(customerID);
			tx.commit();
			return loans;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Loan addLoan(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Transaction tx = TransactionFactory.create();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String loanID = request.getString("id");
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			LoanDAO loanDAO = (LoanDAO) __DaoFactory.create(Loan.class);
			Loan loan = loanDAO.findById(Long.valueOf(loanID));
			Customer customer = customerDAO.findById(customerID);
			customer.addLoan(loan);
			tx.commit();
			return loan;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Loan createLoan(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Transaction tx = TransactionFactory.create();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String loanNumber = request.getString("loan_number");
			String amount = request.getString("amount");
			String branchID = request.getString("branch_id");
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) __DaoFactory.create(Loan.class);
			BranchDAO branchDAO = (BranchDAO) __DaoFactory.create(Branch.class);
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			Customer customer = customerDAO.findById(customerID);
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
			Loan createdLoan = loanDAO.persist(loan);
			customer.addLoan(createdLoan);
			tx.commit();
			return createdLoan;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}


}
