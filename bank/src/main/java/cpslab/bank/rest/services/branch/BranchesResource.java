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
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.util.db.DatabaseTransaction;
import cpslab.bank.util.db.DatabaseTransactionManager;

public class BranchesResource extends ServerResource {

	@Get("application/json")
	public List<Branch> getBranches() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			List<Branch> branches = branchDAO.findAll();
			transaction.commit();
			return branches;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Branch createBranch(Representation entity)
			throws InterruptedException, HibernateException, JSONException, IOException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String branchName = request.getString("name");
			String branchCity = request.getString("city");
			String branchAssets = request.getString("assets");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Branch branch = new Branch(branchName, branchCity, new BigDecimal(branchAssets));
			Branch createdBranch = branchDAO.persist(branch);
			transaction.commit();
			return createdBranch;
		} catch (Exception e) {
			System.out.println(e);
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
