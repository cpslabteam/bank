package cpslab.bank.rest.services.customer;

import org.json.JSONObject;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;

public class CustomerLoanResource extends BaseResource
		implements JsonGetService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		Loan loan = loanDAO.findCustomerLoan(getIdAttribute("customer"), getIdAttribute("loan"));
		String response = EntityJsonSerializer.serialize(loan);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Loan loan = loanDAO.findCustomerLoan(getIdAttribute("customer"), getIdAttribute("loan"));
		Customer customer = customerDAO.loadById(getIdAttribute("customer"));
		boolean success = customer.removeLoan(loan);
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}
}
