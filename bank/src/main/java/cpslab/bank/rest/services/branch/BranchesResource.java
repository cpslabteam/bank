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
		String branchName = requestParams.getString("name");
		String branchCity = requestParams.getString("city");
		String branchAssets = requestParams.getString("assets");
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Branch branch = new Branch(branchName, branchCity, new BigDecimal(branchAssets));
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
