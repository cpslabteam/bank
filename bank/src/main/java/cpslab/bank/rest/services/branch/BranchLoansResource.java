package cpslab.bank.rest.services.branch;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class BranchLoansResource extends ServerResource {

	private Long branchID;

	@Override
	protected void doInit() throws ResourceException {
		branchID = Long.valueOf(getAttribute("branch"));
	}

	@Get("application/json")
	public List<Loan> getLoans() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			LoanDAO loanDAO = (LoanDAO) r.createDao(Loan.class);
			List<Loan> loans = loanDAO.findBranchLoans(branchID);
			r.closeTransaction();
			return loans;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Post("application/json")
	public Loan createLoan(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String loanNumber = request.getString("loan_number");
			String amount = request.getString("amount");
			r.openTransaction();
			
			LoanDAO loanDAO = (LoanDAO) r.createDao(Loan.class);
			BranchDAO branchDAO = (BranchDAO) r.createDao(Branch.class);
			Branch branch = branchDAO.findById(branchID);
			Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
			Loan createdLoan = loanDAO.persist(loan);
			r.closeTransaction();
			return createdLoan;
		} catch (Exception e) {
			System.out.println(e);
			r.rollbackTransaction();

			throw e;
		}
	}
}
