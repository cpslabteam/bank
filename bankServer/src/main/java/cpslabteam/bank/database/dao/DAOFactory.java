package cpslabteam.bank.database.dao;

public abstract class DAOFactory {

	/**
	 * Creates a standalone DAOFactory that returns unmanaged DAO for use in any
	 * environment Hibernate has been configured for.
	 */
	public static final Class<HibernateDAOFactory> HIBERNATE = cpslabteam.bank.database.dao.HibernateDAOFactory.class;

	/**
	 * Factory method for instantiation of concrete factories.
	 */
	public static DAOFactory instance(Class<?> factory) {
		try {
			return (DAOFactory) factory.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException("Couldn't create DAOFactory: " + factory);
		}
	}

	public abstract AccountDAO getAccountDAO();

	public abstract BorrowerDAO getBorrowerDAO();

	public abstract BranchDAO getBranchDAO();

	public abstract CustomerDAO getCustomerDAO();

	public abstract DepositorDAO getDepositorDAO();

	public abstract LoanDAO getLoanDAO();
}