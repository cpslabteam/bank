package cpslab.bank.rest.services.loan;

import java.util.List;

import org.json.JSONObject;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonGetService;
import cpslab.util.rest.services.JsonPutService;

public class LoanOwnersResource extends BaseResource implements JsonGetService, JsonPutService {

	@Override
	public String handleGet() throws Throwable {
		getRepository().openTransaction();
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		List<Customer> owners = customerDAO.findLoanOwners(getIdAttribute("loan"));
		String response = EntityJsonSerializer.serialize(owners);
		getRepository().closeTransaction();
		return response;
	}

	@Override
	public String handlePut(JSONObject requestParams) throws Throwable {
		long ownerID = requestParams.getLong("id");
		getRepository().openTransaction();
		LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class);
		CustomerDAO customerDAO = (CustomerDAO) getRepository().createDao(Customer.class);
		Customer owner = customerDAO.loadById(ownerID);
		Loan loan = loanDAO.loadById(getIdAttribute("loan"));
		loan.addOwner(owner);
		String response = EntityJsonSerializer.serialize(owner);
		getRepository().closeTransaction();
		return response;
	}
}
