package cpslab.bank.rest.services.loan;

import java.util.List;

import org.json.JSONArray;
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
		long transactionId = getRepository().openTransaction();
		try {
			LoanDAO loanDAO = (LoanDAO) getRepository().createDao(Loan.class, transactionId);
			BranchDAO branchDAO =
					(BranchDAO) getRepository().createDao(Branch.class, transactionId);
			Loan loan = new Loan();
			if(requestParams.has("branch_id")){
				long branchID = requestParams.getLong("branch_id");
				loan.setBranch(branchDAO.loadById(branchID));
			}
			if(requestParams.has("amount")){
				String amount = requestParams.getString("amount");
				loan.setAmount(Double.valueOf(amount));
			}
			if(requestParams.has("loanNumber")){
				String loanNumber = requestParams.getString("loanNumber");
				List<Loan> loans = loanDAO.findByLoanNumber(loanNumber);
				if(!loans.isEmpty())
					throw new IllegalArgumentException("Loan Number must be unique");
				loan.setLoanNumber(loanNumber);
			}
			Loan createdLoan = loanDAO.persist(loan);
			if(requestParams.has("owners")){
				CustomerDAO customerDAO =
						(CustomerDAO) getRepository().createDao(Customer.class, transactionId);
				JSONArray owners = requestParams.getJSONArray("owners");
				for (int i = 0; i < owners.length(); i++) {
					long ownerId = owners.getJSONObject(i).getLong("id");
					Customer owner = customerDAO.loadById(ownerId);
					loan.addOwner(owner);
				}
			}
			String response = EntityJsonSerializer.serialize(createdLoan);
			getRepository().closeTransaction(transactionId);
			return response;
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}
}
