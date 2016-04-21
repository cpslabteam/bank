package cpslab.bank.server.restapi.resources.customer;

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

import cpslab.bank.database.dao.BranchDAO;
import cpslab.bank.database.dao.CustomerDAO;
import cpslab.bank.database.dao.DAOFactory;
import cpslab.bank.database.dao.LoanDAO;
import cpslab.bank.database.objects.Branch;
import cpslab.bank.database.objects.Customer;
import cpslab.bank.database.objects.Loan;
import cpslab.bank.database.transaction.DatabaseTransaction;
import cpslab.bank.database.transaction.DatabaseTransactionManager;

public class CustomerLoansResource extends ServerResource {

	private Long customerID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
	}

	@Get("application/json")
	public List<Loan> getLoans() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			List<Loan> loans = loanDAO.findCustomerLoans(customerID);
			transaction.commit();
			return loans;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Loan addLoan(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String loanID = request.getString("id");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findById(Long.valueOf(loanID));
			Customer customer = customerDAO.findById(customerID);
			customer.addLoan(loan);
			transaction.commit();
			return loan;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Loan createLoan(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String loanNumber = request.getString("loan_number");
			String amount = request.getString("amount");
			String branchID = request.getString("branch_id");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer customer = customerDAO.findById(customerID);
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
			Loan createdLoan = loanDAO.persist(loan);
			customer.addLoan(createdLoan);
			transaction.commit();
			return createdLoan;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}


}
