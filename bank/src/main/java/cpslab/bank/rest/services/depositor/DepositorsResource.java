package cpslab.bank.rest.services.depositor;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class DepositorsResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getDepositors(Representation entity)
			throws InterruptedException, JsonProcessingException, HibernateException {
		Repository r = RepositoryService.getInstance();
		try {
			r.openTransaction();
			
			CustomerDAO customerDAO = (CustomerDAO) r.createDao(Customer.class);
			List<Customer> depositors = customerDAO.findDepositors();
			r.closeTransaction();
			return depositors;
		} catch (Exception e) {
			r.rollbackTransaction();

			throw e;
		}
	}
}
