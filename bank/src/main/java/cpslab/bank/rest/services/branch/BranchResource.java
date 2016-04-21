package cpslab.bank.rest.services.branch;

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

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.util.db.DatabaseTransaction;
import cpslab.bank.util.db.DatabaseTransactionManager;

public class BranchResource extends ServerResource {

	private Long branchID;

	@Override
	protected void doInit() throws ResourceException {
		branchID = Long.valueOf(getAttribute("branch"));
	}

	@Get("application/json")
	public Branch getBranch() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Branch branch = branchDAO.findById(branchID);
			transaction.commit();
			return branch;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Branch updateBranch(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String name = request.getString("name");
			String city = request.getString("city");
			String assets = request.getString("assets");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Branch branch = branchDAO.findById(branchID);
			branch.setName(name);
			branch.setCity(city);
			branch.setAssets(new BigDecimal(assets));
			Branch updatedBranch = branchDAO.update(branch);
			transaction.commit();
			return updatedBranch;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteBranch() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Branch branch = branchDAO.findById(branchID);
			branchDAO.delete(branch);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
