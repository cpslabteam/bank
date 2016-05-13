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
		getRepository().openTransaction();
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		Branch branch = branchDAO.loadById(getIdAttribute("branch"));
		String response = EntityJsonSerializer.serialize(branch);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		String name = requestParams.getString("name");
		String city = requestParams.getString("city");
		String assets = requestParams.getString("assets");
		getRepository().openTransaction();
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		Branch branch = branchDAO.loadById(getIdAttribute("branch"));
		branch.setName(name);
		branch.setCity(city);
		branch.setAssets(new BigDecimal(assets));
		Branch updatedBranch = branchDAO.update(branch);
		String response = EntityJsonSerializer.serialize(updatedBranch);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		Branch branch = branchDAO.loadById(getIdAttribute("branch"));
		boolean success = branchDAO.deleteById(branch.getId());
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}
}
