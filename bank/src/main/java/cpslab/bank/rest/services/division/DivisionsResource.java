package cpslab.bank.rest.services.division;

import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.DivisionDAO;
import cpslab.bank.api.entities.Division;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;

public class DivisionsResource extends BaseResource implements JsonGetService, JsonPostService{
	

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			DivisionDAO divisionDAO =
					(DivisionDAO) getRepository().createDao(Division.class, transactionId);
			List<Division> divisions = divisionDAO.findAll();
			String response = EntityJsonSerializer.serialize(divisions);
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
			DivisionDAO divisionDAO =
					(DivisionDAO) getRepository().createDao(Division.class, transactionId);
			Division division = new Division();
			if (requestParams.has("name"))
				division.setName(requestParams.getString("name"));
			Division createdDivision = divisionDAO.persist(division);
			String response = EntityJsonSerializer.serialize(createdDivision);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
