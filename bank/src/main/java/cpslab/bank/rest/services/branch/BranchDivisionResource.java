package cpslab.bank.rest.services.branch;

import org.json.JSONObject;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.DivisionDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Division;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class BranchDivisionResource extends BaseResource
		implements JsonGetService, JsonPutService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao((Branch.class), transactionId);
			Branch branch = branchDAO.loadById(getIdAttribute("branch"));
			Division division = branch.getDivision();
			String response = EntityJsonSerializer.serialize(division);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		long divisionID = requestParams.getLong("id");
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao((Branch.class), transactionId);
			DivisionDAO divisionDAO =
					(DivisionDAO) getRepository().createDao((Division.class), transactionId);
			Branch branch = branchDAO.loadById(getIdAttribute("branch"));
			Division division = divisionDAO.loadById(divisionID);
			branch.setDivision(division);
			branchDAO.update(branch);
			String response = EntityJsonSerializer.serialize(division);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
