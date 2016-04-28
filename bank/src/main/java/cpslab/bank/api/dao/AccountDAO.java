package cpslab.bank.api.dao;

import java.util.List;

import cpslab.bank.api.entities.Account;
import cpslab.util.db.Dao;

public interface AccountDAO extends Dao<Account> {

	public List<Account> findCustomerAccounts(Long customerID);
	
	public List<Account> findBranchAccounts(Long branchID);

	public Account findCustomerAccount(Long customerID, Long accountID);
	
	public Account findBranchAccount(Long branchID, Long accountID);
}
