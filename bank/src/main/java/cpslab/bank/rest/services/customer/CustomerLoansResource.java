package cpslab.bank.rest.services.customer;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPostService;
import cpslab.util.rest.services.JsonPutService;

public class CustomerLoansResource extends BaseResource
		implements JsonGetService, JsonPutService, JsonPostService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		List<Loan> loans = loanDAO.findCustomerLoans(getIdAttribute("customer"));
		String response = EntityJsonSerializer.serialize(loans);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		long loanID = requestParams.getLong("id");
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		Loan loan = loanDAO.loadById(loanID);
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		customer.addLoan(loan);
		String response = EntityJsonSerializer.serialize(loan);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		String loanNumber = requestParams.getString("loan_number");
		String amount = requestParams.getString("amount");
		long branchID = requestParams.getLong("branch_id");
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		BranchDAO branchDAO = (BranchDAO) getRepository().createDao(Branch.class);
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		Branch branch = branchDAO.loadById(branchID);
		Loan loan = new Loan(loanNumber, branch, new BigDecimal(amount));
		Loan createdLoan = loanDAO.persist(loan);
		customer.addLoan(createdLoan);
		String response = EntityJsonSerializer.serialize(createdLoan);
		getRepository().closeTransaction();
		return response;
	}
}
