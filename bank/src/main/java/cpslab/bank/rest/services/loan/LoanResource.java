package cpslab.bank.rest.services.loan;

import java.io.IOException;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class LoanResource extends ServerResource {

	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Get("application/json")
	public Loan getLoan() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			LoanDAO loanDAO = (LoanDAO) r.createDao(Loan.class);
			Loan loan = loanDAO.findById(loanID);
			r.closeTransaction();
			return loan;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Post("application/json")
	public Loan updateLoan(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String loanNumber = request.getString("loan_number");
			String amount = request.getString("amount");
			r.openTransaction();
			
			LoanDAO loanDAO = (LoanDAO) r.createDao(Loan.class);
			Loan loan = loanDAO.findById(loanID);
			loan.setLoanNumber(loanNumber);
			loan.setAmount(new BigDecimal(amount));
			Loan updatedLoan = loanDAO.update(loan);
			r.closeTransaction();
			return updatedLoan;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Delete("application/json")
	public void deleteLoan() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			LoanDAO loanDAO = (LoanDAO) r.createDao(Loan.class);
			Loan loan = loanDAO.findById(loanID);
			loanDAO.delete(loan);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
