package cpslab.bank.rest.services.division;

import java.util.List;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;

public class DivisionBranchesResource extends BaseResource implements JsonGetService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			List<Branch> branches = branchDAO.findDivisionBranches(getIdAttribute("division"));
			String response = EntityJsonSerializer.serialize(branches);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
