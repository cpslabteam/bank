package cpslab.bank.rest.services.customer;

import java.math.BigDecimal;

import org.json.JSONObject;

import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.jsonserialization.EntityJsonSerializer;
import cpslab.bank.rest.services.BaseResource;
import cpslab.util.rest.services.JsonPostService;

public class CustomerLoanDepositResource extends BaseResource implements JsonPostService {

	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		String amount = requestParams.getString("amount");
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			Loan loan =
					loanDAO.findCustomerLoan(getIdAttribute("customer"), getIdAttribute("loan"));
			BigDecimal newAmount = loan.getAmount().subtract(new BigDecimal(amount));
			loan.setAmount(newAmount);
			Loan updatedLoan = loanDAO.update(loan);
			String response = EntityJsonSerializer.serialize(updatedLoan);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
