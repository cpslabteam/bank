package cpslab.bank.server.restapi.resources.borrower;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslab.bank.database.dao.CustomerDAO;
import cpslab.bank.database.dao.DAOFactory;
import cpslab.bank.database.objects.Customer;
import cpslab.bank.database.transaction.DatabaseTransaction;
import cpslab.bank.database.transaction.DatabaseTransactionManager;

public class BorrowersResource extends ServerResource {

	@Get("application/json")
	public List<Customer> getBorrowers() throws InterruptedException, JsonProcessingException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
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
