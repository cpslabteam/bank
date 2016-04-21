package cpslab.bank.rest.services.loan;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;

public class LoanBranchResource extends ServerResource {

	private Long loanID;

	@Override
	protected void doInit() throws ResourceException {
		loanID = Long.valueOf(getAttribute("loan"));
	}

	@Get("application/json")
	public Branch getBranch() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) DAOFactory.createDao(Loan.class);
			Loan loan = loanDAO.findById(loanID);
			Branch branch = loan.getBranch();
			tx.commit();
			return branch;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Branch changeBranch(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String branchID = request.getString("id");
			tx.begin();
			
			LoanDAO loanDAO = (LoanDAO) DAOFactory.createDao(Loan.class);
			BranchDAO branchDAO = (BranchDAO) DAOFactory.createDao(Branch.class);
			Loan loan = loanDAO.findById(loanID);
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			loan.setBranch(branch);
			loanDAO.update(loan);
			tx.commit();
			return branch;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

}
