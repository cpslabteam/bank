package cpslab.bank.rest.services.loan;

import org.json.JSONObject;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class LoanBranchResource extends BaseResource implements JsonGetService, JsonPutService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			Loan loan = loanDAO.loadById(getIdAttribute("loan"));
			Branch branch = loan.getBranch();
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
		long branchID = requestParams.getLong("id");
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Loan loan = loanDAO.loadById(getIdAttribute("loan"));
			Branch branch = branchDAO.loadById(branchID);
			loan.setBranch(branch);
			loanDAO.update(loan);
			String response = EntityJsonSerializer.serialize(branch);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
