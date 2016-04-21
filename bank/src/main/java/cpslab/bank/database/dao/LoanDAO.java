package cpslab.bank.database.dao;

import java.util.List;

import cpslab.bank.database.objects.Loan;

public interface LoanDAO extends GenericDAO<Loan, Long> {

	List<Loan> findBranchLoans(Long branchID);

	Loan findBranchLoan(Long branchID, Long loanID);

	List<Loan> findCustomerLoans(Long customerID);
	
	Loan findCustomerLoan(Long customerID, Long loanID);

}
