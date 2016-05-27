package cpslab.bank.rest.services.branch;

import java.math.BigDecimal;

import org.json.JSONObject;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class BranchResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Branch branch = branchDAO.loadById(getIdAttribute("branch"));
			String response = EntityJsonSerializer.serialize(branch);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Branch branch = branchDAO.loadById(getIdAttribute("branch"));
			if (requestParams.has("name"))
				branch.setName(requestParams.getString("name"));
			if (requestParams.has("city"))
				branch.setCity(requestParams.getString("city"));
			if (requestParams.has("assets"))
				branch.setAssets(new BigDecimal(requestParams.getString("assets")));
			Branch updatedBranch = branchDAO.update(branch);
			String response = EntityJsonSerializer.serialize(updatedBranch);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Branch branch = branchDAO.loadById(getIdAttribute("branch"));
			boolean success = branchDAO.deleteById(branch.getId());
			getRepository().closeTransaction(transactionId);
			return new JSONObject().put("success", success).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
