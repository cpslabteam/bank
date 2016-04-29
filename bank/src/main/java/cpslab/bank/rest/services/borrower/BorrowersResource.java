package cpslab.bank.rest.services.borrower;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class BorrowersResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getBorrowers() throws InterruptedException, JsonProcessingException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			List<Customer> borrowers = customerDAO.findBorrowers();
			r.closeTransaction();
			return borrowers;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
