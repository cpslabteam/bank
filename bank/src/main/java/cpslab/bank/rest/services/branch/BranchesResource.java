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
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class BranchesResource extends ServerResource {

	@Get("application/json")
	public List<Branch> getBranches() throws InterruptedException, IOException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			BranchDAO branchDAO = (BranchDAO) r.createDao(Branch.class);
			List<Branch> branches = branchDAO.findAll();
			r.closeTransaction();
			return branches;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Post("application/json")
	public Branch createBranch(Representation entity)
			throws InterruptedException, HibernateException, JSONException, IOException {
		Repository r = RepositoryService.getInstance();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String branchName = request.getString("name");
			String branchCity = request.getString("city");
			String branchAssets = request.getString("assets");
			r.openTransaction();
			
			BranchDAO branchDAO = (BranchDAO) r.createDao(Branch.class);
			Branch branch = new Branch(branchName, branchCity, new BigDecimal(branchAssets));
			Branch createdBranch = branchDAO.persist(branch);
			r.closeTransaction();
			return createdBranch;
		} catch (Exception e) {
			System.out.println(e);
			r.rollbackTransaction();

			throw e;
		}
	}
}
