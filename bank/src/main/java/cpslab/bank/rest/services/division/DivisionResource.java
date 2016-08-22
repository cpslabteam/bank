package cpslab.bank.rest.services.division;

import org.json.JSONObject;

import cpslab.bank.api.dao.DivisionDAO;
import cpslab.bank.api.entities.Division;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class DivisionResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			DivisionDAO divisionDAO =
					(DivisionDAO) getRepository().createDao(Division.class, transactionId);
			Division division = divisionDAO.loadById(getIdAttribute("division"));
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
		long transactionId = getRepository().openTransaction();
		try {
			DivisionDAO divisionDAO =
					(DivisionDAO) getRepository().createDao(Division.class, transactionId);
			Division division = divisionDAO.loadById(getIdAttribute("division"));
			if (requestParams.has("name"))
				division.setName(requestParams.getString("name"));
			Division updatedDivision = divisionDAO.update(division);
			String response = EntityJsonSerializer.serialize(updatedDivision);
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
			DivisionDAO divisionDAO =
					(DivisionDAO) getRepository().createDao(Division.class, transactionId);
			Division division = divisionDAO.loadById(getIdAttribute("division"));
			boolean success = divisionDAO.deleteById(division.getId());
			getRepository().closeTransaction(transactionId);
			return new JSONObject().put("success", success).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
