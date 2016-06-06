package cpslab.bank.rest.services.branch;

import java.math.BigDecimal;

import org.json.JSONObject;

import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class BranchLoanResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			Loan loan = loanDAO.findBranchLoan(getIdAttribute("branch"), getIdAttribute("loan"));
			String response = EntityJsonSerializer.serialize(loan);
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
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			Loan loan = loanDAO.findBranchLoan(getIdAttribute("branch"), getIdAttribute("loan"));
			if (requestParams.has("loan_number"))
				loan.setLoanNumber(requestParams.getString("loan_number"));
			if (requestParams.has("amount"))
				loan.setAmount(new BigDecimal(requestParams.getString("amount")));
			Loan updatedLoan = loanDAO.update(loan);
			String response = EntityJsonSerializer.serialize(updatedLoan);
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
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			Loan loan = loanDAO.findBranchLoan(getIdAttribute("branch"), getIdAttribute("loan"));
			boolean success = loanDAO.deleteById(loan.getId());
			getRepository().closeTransaction(transactionId);
			return new JSONObject().put("success", success).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
