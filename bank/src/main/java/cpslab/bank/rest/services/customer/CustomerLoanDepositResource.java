package cpslab.bank.rest.services.customer;

import java.io.IOException;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class CustomerLoanDepositResource extends ServerResource {

	private Long customerID;
	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = Long.valueOf(getAttribute("customer"));
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Post("application/json")
	public Loan deposit(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String amount = request.getString("amount");
			r.openTransaction();
			
			LoanDAO loanDAO = (LoanDAO) r.createDao(Loan.class);
			Loan loan = loanDAO.findCustomerLoan(customerID, loanID);
			BigDecimal newBalance = loan.getAmount().subtract(new BigDecimal(amount));
			loan.setAmount(newBalance);
			Loan updatedLoan = loanDAO.update(loan);
			r.closeTransaction();
			return updatedLoan;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
