package cpslab.bank.api.dao;

import java.util.List;

import cpslab.bank.api.entities.Loan;
import cpslab.bank.util.db.GenericDAO;

public interface LoanDAO
        extends GenericDAO<Loan> {

    List<Loan> findBranchLoans(Long branchID);

    Loan findBranchLoan(Long branchID, Long loanID);

    List<Loan> findCustomerLoans(Long customerID);

    Loan findCustomerLoan(Long customerID, Long loanID);

}
