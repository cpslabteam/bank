package cpslab.bank.rest.services.loan;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;

public class LoansResource extends BaseResource implements JsonGetService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			List<Loan> loans = loanDAO.findAll();
			String response = EntityJsonSerializer.serialize(loans);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String loanNumber = requestParams.getString("loan_number");
		String amount = requestParams.getString("amount");
		long branchID = requestParams.getLong("branch_id");
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Branch branch = branchDAO.loadById(branchID);
			Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
			Loan createdLoan = loanDAO.persist(loan);
			String response = EntityJsonSerializer.serialize(createdLoan);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
