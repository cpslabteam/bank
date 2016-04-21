package cpslab.bank.api.dao;

import java.util.List;

import cpslab.bank.api.entities.Customer;
import cpslab.bank.util.db.GenericDAO;

public interface CustomerDAO extends GenericDAO<Customer> {

	public List<Customer> findDepositors();
	
	public List<Customer> findBorrowers();
	
	public List<Customer> findAccountOwners(Long accountID);
	
	public List<Customer> findLoanOwners(Long loanID);
	
	public Customer findAccountOwner(Long accountID, Long ownerID);
	
	public Customer findLoanOwner(Long loanID, Long ownerID);
}
