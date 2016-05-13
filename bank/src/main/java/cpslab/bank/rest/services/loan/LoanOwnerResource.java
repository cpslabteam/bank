package cpslab.bank.rest.services.loan;

import org.json.JSONObject;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;
import cpslab.util.rest.services.JsonGetService;

public class LoanOwnerResource extends BaseResource implements JsonGetService, JsonDeleteService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer owner = customerDAO.findLoanOwner(getIdAttribute("loan"), getIdAttribute("owner"));
		String response = EntityJsonSerializer.serialize(owner);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Loan loan = loanDAO.loadById(getIdAttribute("loan"));
		Customer owner = customerDAO.loadById(getIdAttribute("owner"));
		boolean success = loan.removeOwner(owner);
		getRepository().closeTransaction();
		return new JSONObject().put("success", success).toString();
	}
}
