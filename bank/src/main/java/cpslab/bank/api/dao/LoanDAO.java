package cpslab.bank.api.dao;

import java.util.List;

import cpslab.bank.api.entities.Loan;
import cpslab.util.db.Dao;

public interface LoanDAO
        extends Dao<Loan> {

    List<Loan> findBranchLoans(Long branchID);

    Loan findBranchLoan(Long branchID, Long loanID);

    List<Loan> findCustomerLoans(Long customerID);

    Loan findCustomerLoan(Long customerID, Long loanID);
    
	public List<Loan> findByLoanNumber(String loanNumber);


}
