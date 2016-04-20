package cpslabteam.bank.server.restapi.resources.customer;

import java.io.IOException;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.dao.LoanDAO;
import cpslabteam.bank.database.objects.Loan;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

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
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String amount = request.getString("amount");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findCustomerLoan(customerID, loanID);
			BigDecimal newBalance = loan.getAmount().subtract(new BigDecimal(amount));
			loan.setAmount(newBalance);
			Loan updatedLoan = loanDAO.update(loan);
			transaction.commit();
			return updatedLoan;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
