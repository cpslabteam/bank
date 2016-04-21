package cpslab.bank.database.dao;

import org.hibernate.Session;

import cpslab.bank.database.SessionManager;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public AccountDAO getAccountDAO() {
		return (AccountDAO) instantiateDAO(AccountDAOHibernate.class);
	}

	@Override
	public BranchDAO getBranchDAO() {
		return (BranchDAO) instantiateDAO(BranchDAOHibernate.class);
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		return (CustomerDAO) instantiateDAO(CustomerDAOHibernate.class);
	}

	@Override
	public LoanDAO getLoanDAO() {
		return (LoanDAO) instantiateDAO(LoanDAOHibernate.class);
	}

	private HibernateDAO<?, ?> instantiateDAO(Class<?> daoClass) {
		try {
			HibernateDAO<?, ?> dao = (HibernateDAO<?, ?>) daoClass.newInstance();
			dao.setSession(getCurrentSession());
			return dao;
		} catch (Exception ex) {
			throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
		}
	}

	protected Session getCurrentSession() {
		return SessionManager.getSession();
	}

}
