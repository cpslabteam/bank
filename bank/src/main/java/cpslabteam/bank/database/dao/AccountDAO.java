package cpslabteam.bank.database.dao;

import java.util.List;

import cpslabteam.bank.database.objects.Account;

public interface AccountDAO extends GenericDAO<Account, Long> {

	public List<Account> findCustomerAccounts(Long customerID);
	
	public List<Account> findBranchAccounts(Long branchID);

	public Account findCustomerAccount(Long customerID, Long accountID);
	
	public Account findBranchAccount(Long branchID, Long accountID);
}
