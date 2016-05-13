package cpslab.bank.rest.services.borrower;

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

public class BorrowerResource extends ServerResource {

	private Long borrowerID;

	@Override
	protected void doInit() throws ResourceException {
		borrowerID = Long.valueOf(getAttribute("borrower"));
	}

	@Get("application/json")
	public Customer getBorrower() throws InterruptedException, JsonProcessingException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer borrower = customerDAO.loadById(borrowerID);
			r.closeTransaction();
			return borrower;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}

	@Post("application/json")
	public Customer updateCustomer(Customer borrower)
			throws InterruptedException, JsonProcessingException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO borrowerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer updatedCustomer = borrowerDAO.update(borrower);
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
			
			CustomerDAO borrowerDAO = (CustomerDAO) r.createDao(Customer.class);
			Customer borrower = borrowerDAO.loadById(borrowerID);
			borrowerDAO.delete(borrower);
			r.closeTransaction();
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
