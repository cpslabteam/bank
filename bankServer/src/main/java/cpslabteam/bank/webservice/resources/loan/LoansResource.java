package cpslabteam.bank.webservice.resources.loan;

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

import cpslabteam.bank.database.dao.BranchDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.dao.LoanDAO;
import cpslabteam.bank.database.objects.Branch;
import cpslabteam.bank.database.objects.Loan;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class LoansResource extends ServerResource {

	@Get("application/json")
	public List<Loan> getLoans() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			List<Loan> loans = loanDAO.findAll();
			transaction.commit();
			return loans;
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
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
			Loan createdLoan = loanDAO.persist(loan);
			transaction.commit();
			return createdLoan;
		} catch (Exception e) {
			System.out.println(e);
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
