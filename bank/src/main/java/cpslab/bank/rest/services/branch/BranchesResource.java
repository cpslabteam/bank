package cpslab.bank.rest.services.branch;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;

public class BranchesResource extends BaseResource implements JsonGetService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		List<Branch> branches = branchDAO.findAll();
		String response = EntityJsonSerializer.serialize(branches);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String branchName = requestParams.getString("name");
		String branchCity = requestParams.getString("city");
		String branchAssets = requestParams.getString("assets");
		getRepository().openTransaction();
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		Branch branch = new Branch(branchName, branchCity, new BigDecimal(branchAssets));
		Branch createdBranch = branchDAO.persist(branch);
		String response = EntityJsonSerializer.serialize(createdBranch);
		getRepository().closeTransaction();
		return response;
	}
}
