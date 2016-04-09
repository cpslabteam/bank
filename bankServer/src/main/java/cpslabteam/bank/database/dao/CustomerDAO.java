package cpslabteam.bank.database.dao;

import java.util.List;

import cpslabteam.bank.database.objects.Customer;

public interface CustomerDAO extends GenericDAO<Customer, Long> {

	public List<Customer> findDepositors();
	
	public List<Customer> findBorrowers();
}
