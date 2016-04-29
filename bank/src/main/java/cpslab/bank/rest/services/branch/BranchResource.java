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
import cpslab.bank.api.entities.Branch;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class BranchResource extends ServerResource {

	private Long branchID;

	@Override
	protected void doInit() throws ResourceException {
		branchID = Long.valueOf(getAttribute("branch"));
	}

	@Get("application/json")
	public Branch getBranch() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			BranchDAO branchDAO = (BranchDAO) r.createDao(Branch.class);
			Branch branch = branchDAO.findById(branchID);
			r.closeTransaction();
			return branch;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Put("application/json")
	public Branch updateBranch(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String name = request.getString("name");
			String city = request.getString("city");
			String assets = request.getString("assets");
			r.openTransaction();
			
			BranchDAO branchDAO = (BranchDAO) r.createDao(Branch.class);
			Branch branch = branchDAO.findById(branchID);
			branch.setName(name);
			branch.setCity(city);
			branch.setAssets(new BigDecimal(assets));
			Branch updatedBranch = branchDAO.update(branch);
			r.closeTransaction();
			return updatedBranch;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Delete("application/json")
	public void deleteBranch() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			BranchDAO branchDAO = (BranchDAO) r.createDao(Branch.class);
			Branch branch = branchDAO.findById(branchID);
			branchDAO.delete(branch);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
