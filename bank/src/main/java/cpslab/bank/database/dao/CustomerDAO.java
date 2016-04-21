package cpslab.bank.database.dao;

import java.util.List;

import cpslab.bank.database.objects.Customer;

public interface CustomerDAO extends GenericDAO<Customer, Long> {

	public List<Customer> findDepositors();
	
	public List<Customer> findBorrowers();
	
	public List<Customer> findAccountOwners(Long accountID);
	
	public List<Customer> findLoanOwners(Long loanID);
	
	public Customer findAccountOwner(Long accountID, Long ownerID);
	
	public Customer findLoanOwner(Long loanID, Long ownerID);
}
