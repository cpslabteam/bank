package cpslab.bank.rest.api;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONException;

import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;
import cpslab.bank.internal.dao.HibernateAccountDAO;
import cpslab.bank.internal.dao.HibernateBranchDAO;
import cpslab.bank.internal.dao.HibernateCustomerDAO;
import cpslab.bank.internal.dao.HibernateLoanDAO;
import cpslab.bank.rest.handlers.hibernate.ConstraintViolationExceptionHandler;
import cpslab.bank.rest.handlers.hibernate.ObjectNotFoundExceptionHandler;
import cpslab.bank.rest.handlers.json.JSONExceptionHandler;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;
import cpslab.util.rest.RestServer;
import cpslab.util.rest.ServiceExceptionHandlerManager;

public class BankRestServerActivator {

	private static RestServer restServer = RestServer.getInstance();
	private static final BankRestServerActivator DEFAULT_INSTANCE = new BankRestServerActivator();
	private static final int SERVER_PORT = 9192;

	private BankRestServerActivator() {
		registerDaos();
		registerServiceExceptionHandlers();
	}
	
	public static BankRestServerActivator getInstance(){
		return DEFAULT_INSTANCE;
	}

	private void registerDaos() {
		Repository r = RepositoryService.getInstance();
		r.registerDao(Account.class, HibernateAccountDAO.class);
		r.registerDao(Loan.class, HibernateLoanDAO.class);
		r.registerDao(Branch.class, HibernateBranchDAO.class);
		r.registerDao(Customer.class, HibernateCustomerDAO.class);
	}

	private void registerServiceExceptionHandlers() {
		ServiceExceptionHandlerManager.registerHandler(ConstraintViolationException.class,
				ConstraintViolationExceptionHandler.class);
		ServiceExceptionHandlerManager.registerHandler(ObjectNotFoundException.class,
				ObjectNotFoundExceptionHandler.class);
		ServiceExceptionHandlerManager.registerHandler(JSONException.class,
				JSONExceptionHandler.class);
	}

	public void open() throws Exception {
		restServer.setHttpPort(SERVER_PORT);
		DeclaredServerResources.getDeclaredServerResources()
				.forEach((url, resource) -> restServer.addRoute(url, resource));
		restServer.start();
	}

	public void close() throws Exception {
		restServer.stop();
	}

}
