package cpslab.bank.rest.services.loan;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.__DaoFactory;
import cpslab.util.db.Transaction;
import cpslab.util.db.TransactionFactory;

public class LoansResource extends ServerResource {

	@Get("application/json")
	public List<Loan> getLoans() throws InterruptedException, IOException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) __DaoFactory.create(Loan.class);
			List<Loan> loans = loanDAO.findAll();
			tx.commit();
			return loans;
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
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
			Loan createdLoan = loanDAO.persist(loan);
			tx.commit();
			return createdLoan;
		} catch (Exception e) {
			System.out.println(e);
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}
}
