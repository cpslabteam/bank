package cpslabteam.bank.webservice.resources.branch;

import java.io.IOException;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.dao.LoanDAO;
import cpslabteam.bank.database.objects.Loan;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class BranchLoanResource extends ServerResource {

	private Long branchID;
	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		branchID = Long.valueOf(getAttribute("branch"));
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Get("application/json")
	public Loan getLoan() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findBranchLoan(branchID, loanID);
			transaction.commit();
			return loan;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Loan updateLoan(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String loanNumber = request.getString("loan_number");
			String ammount = request.getString("ammount");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findBranchLoan(branchID, loanID);
			loan.setLoanNumber(loanNumber);
			loan.setAmount(new BigDecimal(ammount));
			Loan updatedLoan = loanDAO.update(loan);
			transaction.commit();
			return updatedLoan;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteLoan() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findBranchLoan(branchID, loanID);
			loanDAO.delete(loan);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
