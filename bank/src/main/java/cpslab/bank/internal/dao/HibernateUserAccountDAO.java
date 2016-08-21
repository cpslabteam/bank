package cpslab.bank.internal.dao;

import org.hibernate.Query;

import cpslab.bank.api.dao.UserAccountDAO;
import cpslab.bank.api.entities.UserAccount;
import cpslab.util.db.hibernate.HibernateDao;

public class HibernateUserAccountDAO extends HibernateDao<UserAccount>implements UserAccountDAO {

	private final static String USERNAME_PASSWORD_QUERY =
			"SELECT u FROM UserAccount u WHERE username = :username AND password = :password";

	@Override
	public boolean hasUserPassword(String username, String password) {
		Query query = getSession().createQuery(USERNAME_PASSWORD_QUERY);
        query.setString("username", username);
        query.setString("password", password);
        return !query.list().isEmpty();
	}

}
