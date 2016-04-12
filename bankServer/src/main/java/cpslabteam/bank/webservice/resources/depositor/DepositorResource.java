package cpslabteam.bank.webservice.resources.depositor;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class DepositorResource extends ServerResource {

	private Long depositorID;

	@Override
	protected void doInit() throws ResourceException {
		depositorID = Long.valueOf(getAttribute("depositor"));
	}

	@Get("application/json")
	public Customer getDepositor() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer depositor = customerDAO.findById(depositorID);
			transaction.commit();
			return depositor;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Customer depositor)
			throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO depositorDAO = daoFactory.getCustomerDAO();
			Customer updatedCustomer = depositorDAO.update(depositor);
			transaction.commit();
			return updatedCustomer;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO depositorDAO = daoFactory.getCustomerDAO();
			Customer depositor = depositorDAO.findById(depositorID);
			depositorDAO.delete(depositor);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
