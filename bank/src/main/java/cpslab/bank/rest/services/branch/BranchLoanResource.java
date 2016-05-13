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
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		Loan loan = loanDAO.findBranchLoan(getIdAttribute("branch"), getIdAttribute("loan"));
		String response = EntityJsonSerializer.serialize(loan);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		String loanNumber = requestParams.getString("loan_number");
		String amount = requestParams.getString("amount");
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		Loan loan = loanDAO.findBranchLoan(getIdAttribute("branch"), getIdAttribute("loan"));
		loan.setLoanNumber(loanNumber);
		loan.setAmount(new BigDecimal(amount));
		Loan updatedLoan = loanDAO.update(loan);
		String response = EntityJsonSerializer.serialize(updatedLoan);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		Loan loan = loanDAO.findBranchLoan(getIdAttribute("branch"), getIdAttribute("loan"));
		boolean success = loanDAO.deleteById(loan.getId());
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}
}
