package cpslabteam.bank.server.restapi.resources.loan;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.LoanDAO;
import cpslabteam.bank.database.dao.BranchDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Loan;
import cpslabteam.bank.database.objects.Branch;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class LoanBranchResource extends ServerResource {

	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Get("application/json")
	public Branch getBranch() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findById(loanID);
			Branch branch = loan.getBranch();
			transaction.commit();
			return branch;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Branch changeBranch(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String branchID = request.getString("id");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Loan loan = loanDAO.findById(loanID);
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			loan.setBranch(branch);
			loanDAO.update(loan);
			transaction.commit();
			return branch;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
