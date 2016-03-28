package cpslabteam.bank.database.dao;

import org.hibernate.Session;

import cpslabteam.bank.database.SessionManager;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public AccountDAO getAccountDAO() {
		return (AccountDAO) instantiateDAO(AccountDAOHibernate.class);
	}

	@Override
	public BorrowerDAO getBorrowerDAO() {
		return (BorrowerDAO) instantiateDAO(BorrowerDAOHibernate.class);
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
	public DepositorDAO getDepositorDAO() {
		return (DepositorDAO) instantiateDAO(DepositorDAOHibernate.class);
	}

	@Override
	public LoanDAO getLoanDAO() {
		return (LoanDAO) instantiateDAO(LoanDAOHibernate.class);
	}

	private GenericHibernateDAO<?, ?> instantiateDAO(Class<?> daoClass) {
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
