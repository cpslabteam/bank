package main.java.cpslabteam.bank.database.dao;

import org.hibernate.Session;

import main.java.cpslabteam.bank.database.utils.SessionManager;

public class HibernateDAOFactory extends DAOFactory {

	public BranchDAO getBranchDAO() {
		return (BranchDAO) instantiateDAO(BranchDAOHibernate.class);
	}

	private GenericHibernateDAO<?, ?> instantiateDAO(Class<BranchDAOHibernate> daoClass) {
		try {
			GenericHibernateDAO<?, ?> dao = (GenericHibernateDAO<?, ?>) daoClass.newInstance();
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
