package cpslab.bank.rest.borrower;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.util.db.DatabaseTransaction;
import cpslab.bank.util.db.DatabaseTransactionManager;

public class BorrowersResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getBorrowers() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			List<Customer> borrowers = customerDAO.findBorrowers();
			transaction.commit();
			return borrowers;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}
}
