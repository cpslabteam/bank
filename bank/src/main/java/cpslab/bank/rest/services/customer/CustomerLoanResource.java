package cpslab.bank.rest.services.customer;

import org.json.JSONObject;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonDeleteService;

public class CustomerLoanResource extends BaseResource
		implements JsonDeleteService {

	@Override
	public String handleDelete(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			CustomerDAO customerDAO =
					(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
			Loan loan =
					loanDAO.findCustomerLoan(getIdAttribute("customer"), getIdAttribute("loan"));
			Customer customer = customerDAO.loadById(getIdAttribute("customer"));
			boolean success = customer.removeLoan(loan);
			getRepository().closeTransaction(transactionId);
			return new JSONObject().put("success", success).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
