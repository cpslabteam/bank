package cpslab.bank.rest.services.depositor;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.util.db.__DaoFactory;
import cpslab.util.db.Transaction;
import cpslab.util.db.TransactionFactory;

public class DepositorsResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getDepositors(Representation entity)
			throws InterruptedException, JsonProcessingException, HibernateException {
		Transaction tx = TransactionFactory.create();
		try {
			tx.begin();
			
			CustomerDAO customerDAO = (CustomerDAO) __DaoFactory.create(Customer.class);
			List<Customer> depositors = customerDAO.findDepositors();
			tx.commit();
			return depositors;
		} catch (Exception e) {
			if (tx.canRollback())
				tx.rollback();
			throw e;
		}
	}
}
