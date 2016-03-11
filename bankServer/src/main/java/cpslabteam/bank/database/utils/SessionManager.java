package cpslabteam.bank.database.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import cpslabteam.bank.database.objects.BaseDataObject;

/**
 * Creates and hosts the SessionFactory object necessary to create Hibernate
 * sessions. Each created session is bounded to the thread where it was created.
 * This means that for each thread of execution, there will be a different
 * session. In order to get a session, the method
 * {@link SessionManager#getSession()} should be called. The concurrency between
 * sessions is managed internally by the Hibernate framework.
 * 
 * @see SessionFactory
 * @see Session
 */
public final class SessionManager {
	private static final SessionFactory sessionFactory;
	// There should be one and only one session per thread of execution
	private static final String SESSION_CONTEXT = "thread";
	private static final String DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	private static final String DRIVER_CLASS = "org.postgresql.Driver";
	private static final String DATABASE_USERNAME = "postgres";
	private static final String DATABASE_PASSWORD = "root";
	private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/bank";
	// Shows executed SQL statements
	private static final String DEBUG_SHOW_SQL = "true";
	private static final String DEBUG_FORMAT_SQL = "true";
	// Creates database at startup
	private static final String SCHEMA_DDL = "create";

	static {
		sessionFactory = createSessionFactory();
	}

	/**
	 * Provides the current {@link Session}. If a session does not exist in the
	 * current thread, then a new one is created and bounded to it.
	 * 
	 * @return the session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Configures the SessionFactory by setting properties and mapping classes.
	 * 
	 * @return the Configuration of the SessionFactory
	 */
	private static Configuration configureFactory() {
		Configuration configuration = new Configuration();
		configureProperties(configuration);
		configureMappingClasses(configuration);
		return configuration;
	}

	/**
	 * Configures mapping classes. Every class that represents database tables
	 * or information about them should be listed here, otherwise hibernate will
	 * not find them and will throw an error
	 * 
	 * @param configuration
	 *            the Configuration to add the mapping classes
	 * @return the configuration with the mapping classes added
	 */
	private static Configuration configureMappingClasses(Configuration configuration) {
		configuration.addAnnotatedClass(BaseDataObject.class);
		return configuration;
	}

	/**
	 * Configures the properties of the SessionFactory
	 * 
	 * @param configuration
	 *            the Configuration to add the properties
	 * @return the configuration with the properties added
	 */
	private static Configuration configureProperties(Configuration configuration) {
		configuration.setProperty("hibernate.current_session_context_class", SESSION_CONTEXT);
		configuration.setProperty("hibernate.dialect", DIALECT);
		configuration.setProperty("hibernate.connection.driver_class", DRIVER_CLASS);
		configuration.setProperty("hibernate.connection.username", DATABASE_USERNAME);
		configuration.setProperty("hibernate.connection.password", DATABASE_PASSWORD);
		configuration.setProperty("hibernate.connection.url", DATABASE_URL);
		configuration.setProperty("show_sql", DEBUG_SHOW_SQL);
		configuration.setProperty("format_sql", DEBUG_FORMAT_SQL);
		configuration.setProperty("hibernate.hbm2ddl.auto", SCHEMA_DDL);
		return configuration;
	}

	/**
	 * Creates a SessionFactory.
	 * 
	 * @return the SessionFactory
	 * @throws HibernateException
	 */
	private static SessionFactory createSessionFactory() throws HibernateException {
		Configuration configuration = configureFactory();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private SessionManager() {
	}
}
