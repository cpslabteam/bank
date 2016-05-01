package cpslab.bank.rest.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.internal.dao.HibernateAccountDAO;
import cpslab.bank.internal.dao.HibernateBranchDAO;
import cpslab.bank.internal.dao.HibernateCustomerDAO;
import cpslab.bank.internal.dao.HibernateLoanDAO;
import cpslab.util.db.Repository;
import cpslab.util.db.Repository.Dialect;
import cpslab.util.db.RepositoryService;
import cpslab.util.db.RepositoryService.Orm;
import cpslab.util.db.hibernate.jackson.HibernateJacksonConverter;

public final class BankRestServerActivator extends Application {

	private static Logger logger = LoggerFactory.getLogger(BankRestServerActivator.class);

	private static Component component;

	private static final BankRestServerActivator singleton = new BankRestServerActivator();

	private static final int SERVER_PORT = 9192;

	private static final String ROOT_ADDRESS = "http://localhost:" + SERVER_PORT;

	private final String DATABASE_URL = "localhost:5432/bank";

	private final String DATABASE_USERNAME = "postgres";

	private final String DATABASE_PASSWORD = "root";

	private final Dialect DATABASE_DIALECT = Dialect.POSTGRESQL;

	private final Orm DATABASE_ORM = Orm.HIBERNATE;

	public static void close() throws Exception {
		new Thread() {
			public void run() {
				logger.info("Bundle closing, stopping REST server.");
				try {
					component.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void initializeRepository() {
		RepositoryService.initializeRepository(DATABASE_ORM, DATABASE_DIALECT, DATABASE_URL,
				DATABASE_USERNAME, DATABASE_PASSWORD);
	}

	private void registerDaos() {
		Repository r = RepositoryService.getInstance();
		r.registerDao(Account.class, HibernateAccountDAO.class);
		r.registerDao(Loan.class, HibernateLoanDAO.class);
		r.registerDao(Branch.class, HibernateBranchDAO.class);
		r.registerDao(Customer.class, HibernateCustomerDAO.class);
	}

	public static BankRestServerActivator getInstance() {
		return singleton;
	}

	public static void open() throws Exception {
		new Thread() {
			public void run() {
				try {
					serverStart();
					serverAttach();
					printAvailableEndpoints();
				} catch (InterruptedException e) {
					logger.info("Bundle closing, stopping REST server.");
				}
			}
		}.start();
	}

	private static void printAvailableEndpoints() {
		String intro = "You can use this REST API by addressing the following endpoints:\n";
		String endpoint = ("	" + ROOT_ADDRESS + "%s\n");
		String endpoints = DeclaredServerResources	.getDeclaredServerResourcesURLs()
													.stream()
													.map(url -> String.format(endpoint, url))
													.reduce("", String::concat);
		logger.info(intro + endpoints);
	}

	private static void serverAttach() {
		component	.getDefaultHost()
					.attach(getInstance());
	}

	private static void serverStart() throws InterruptedException {
		component = new Component();

		component	.getServers()
					.add(Protocol.HTTP, SERVER_PORT);

		boolean serverBound = false;
		while (!serverBound) {
			try {
				logger.info("Bundle started, REST server initializing...");
				component.start();
				if (component.isStarted())
					logger.info("REST Server Started in port {}.", SERVER_PORT);
				serverBound = true;
			} catch (Exception e) {
				try {
					component.stop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.error(
						"Port {} is occupied! Check if a server is running already. Trying again in 15 sec.",
						SERVER_PORT);
				Thread.sleep(15000);
			}
		}
	}

	private BankRestServerActivator() {
		initializeRepository();
		registerDaos();
		configureCorsService();
		setStatusService(new BankStatusService());
		setHibernateJacksonConverter();
	}

	private void setHibernateJacksonConverter() {
		List<ConverterHelper> converters = Engine	.getInstance()
													.getRegisteredConverters();
		JacksonConverter jacksonConverter = new JacksonConverter();
		for (ConverterHelper converter : converters) {
			if (converter instanceof JacksonConverter) {
				jacksonConverter = (JacksonConverter) converter;
				break;
			}
		}
		if (jacksonConverter != null) {
			converters.remove(jacksonConverter);
			converters.add(new HibernateJacksonConverter());
		}
	}

	private void configureCorsService() {
		CorsService corsService = new CorsService();
		corsService.setAllowedCredentials(false);
		corsService.setDefaultAllowedMethods(new HashSet<Method>(
				Arrays.asList(Method.GET, Method.PUT, Method.POST, Method.DELETE, Method.OPTIONS)));
		corsService.setAllowedOrigins(new HashSet<>(Arrays.asList("*")));
		corsService.setAllowedHeaders(new HashSet<>(Arrays.asList("Content-Type")));
		getServices().add(corsService);
	}

	@Override
	public synchronized Restlet createInboundRoot() {
		final Router router = new Router(getContext());
		DeclaredServerResources	.getDeclaredServerResources()
								.forEach((url, resource) -> router.attach(url, resource));
		return router;
	}
}
