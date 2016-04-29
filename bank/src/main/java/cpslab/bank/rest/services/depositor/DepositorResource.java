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
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class DepositorResource extends ServerResource {

	private Long depositorID;

	@Override
	protected void doInit() throws ResourceException {
		depositorID = Long.valueOf(getAttribute("depositor"));
	}

	@Get("application/json")
	public Customer getDepositor() throws InterruptedException, JsonProcessingException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer depositor = customerDAO.findById(depositorID);
			r.closeTransaction();
			return depositor;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Customer depositor)
			throws InterruptedException, JsonProcessingException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO depositorDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer updatedCustomer = depositorDAO.update(depositor);
			r.closeTransaction();
			return updatedCustomer;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, JsonProcessingException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO depositorDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer depositor = depositorDAO.findById(depositorID);
			depositorDAO.delete(depositor);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
