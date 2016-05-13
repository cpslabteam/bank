package cpslab.bank.rest.services.branch;

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

public class BranchLoansResource extends BaseResource implements JsonGetService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		List<Loan> loans = loanDAO.findBranchLoans(getIdAttribute("branch"));
		String response = EntityJsonSerializer.serialize(loans);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String loanNumber = requestParams.getString("loan_number");
		String amount = requestParams.getString("amount");
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		Branch branch = branchDAO.loadById(getIdAttribute("branch"));
		Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
		Loan createdLoan = loanDAO.persist(loan);
		String response = EntityJsonSerializer.serialize(createdLoan);
		getRepository().closeTransaction();
		return response;
	}

}
