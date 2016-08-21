package cpslab.bank.api.dao;

import cpslab.bank.api.entities.UserAccount;
import cpslab.util.db.Dao;

public interface UserAccountDAO extends Dao<UserAccount>{
	public boolean hasUserPassword(String username, String password);
}
