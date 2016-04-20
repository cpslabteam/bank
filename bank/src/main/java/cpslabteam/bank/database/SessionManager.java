package cpslabteam.bank.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

/**
 * Creates and hosts the SessionFactory object necessary to create Hibernate
 * sessions. To create the SessionFactory, the hibernate.cfg.xml file is used.
 * Each created session is bounded to the thread where it was created. This
 * means that for each thread of execution, there will be a different session.
 * In order to get a session, the method {@link SessionManager#getSession()}
 * should be called. The concurrency between sessions is managed internally by
 * the Hibernate framework.
 * 
 * @see SessionFactory
 * @see Sessions
 */
public final class SessionManager {
	private static final String CONFIGURATION_FILE_PATH = "hibernate/hibernate.cfg.xml";
	private static final SessionFactory sessionFactory;

	static {
		sessionFactory = createSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
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
	 * Creates a SessionFactory.
	 * 
	 * @return the SessionFactory
	 * @throws HibernateException
	 */
	private static SessionFactory createSessionFactory() throws HibernateException {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure(CONFIGURATION_FILE_PATH)
				.build();
		Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
		return metadata.getSessionFactoryBuilder().build();
	}

	private SessionManager() {
	}
}
