package cpslab.bank.rest.services.depositor;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.__DaoFactory;
import cpslab.util.db.Transaction;
import cpslab.util.db.TransactionFactory;

public class DepositorResource extends ServerResource {

	private Long depositorID;

	@Override
	protected void doInit() throws ResourceException {
		depositorID = Long.valueOf(getAttribute("depositor"));
	}

	@Get("application/json")
	public Customer getDepositor() throws InterruptedException, JsonProcessingException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			Customer depositor = customerDAO.findById(depositorID);
			tx.commit();
			return depositor;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Customer depositor)
			throws InterruptedException, JsonProcessingException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			CustomerDAO depositorDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			Customer updatedCustomer = depositorDAO.update(depositor);
			tx.commit();
			return updatedCustomer;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, JsonProcessingException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			CustomerDAO depositorDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			Customer depositor = depositorDAO.findById(depositorID);
			depositorDAO.delete(depositor);
			tx.commit();
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}
}
