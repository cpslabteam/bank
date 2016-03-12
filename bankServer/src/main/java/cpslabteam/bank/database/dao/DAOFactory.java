package main.java.cpslabteam.bank.database.dao;

public abstract class DAOFactory {

	/**
	 * Creates a standalone DAOFactory that returns unmanaged DAO for use
	 * in any environment Hibernate has been configured for. 
	 */
	public static final Class<HibernateDAOFactory> HIBERNATE = main.java.cpslabteam.bank.database.dao.HibernateDAOFactory.class;

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

	public abstract BranchDAO getBranchDAO();
}
