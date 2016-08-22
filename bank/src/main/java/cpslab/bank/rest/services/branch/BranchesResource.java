package cpslab.bank.rest.services.branch;

import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.DivisionDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Division;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;

public class BranchesResource extends BaseResource implements JsonGetService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			List<Branch> branches = branchDAO.findAll();
			String response = EntityJsonSerializer.serialize(branches);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Branch branch = new Branch();
			if (requestParams.has("name")){
				String name = requestParams.getString("name");
				List<Branch> branches = branchDAO.findByName(name);
				if(!branches.isEmpty())
					throw new IllegalArgumentException("Name must be unique");
				branch.setName(name);
			}
			if (requestParams.has("city"))
				branch.setCity(requestParams.getString("city"));
			if (requestParams.has("assets"))
				branch.setAssets(Double.valueOf(requestParams.getString("assets")));
			if (requestParams.has("division_id")){
				DivisionDAO divisionDAO = (DivisionDAO) getRepository().createDao(Division.class, transactionId);
				Division division = divisionDAO.findById(requestParams.getLong("division_id"));
				branch.setDivision(division);
			}
			Branch createdBranch = branchDAO.persist(branch);
			String response = EntityJsonSerializer.serialize(createdBranch);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
