package cpslabteam.bank.webservice.resources.borrower;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Customer;

public class BorrowerResource extends ServerResource {

	private Long borrowerID;

	@Override
	protected void doInit() throws ResourceException {
		borrowerID = Long.valueOf(getAttribute("borrower"));
	}

	@Get("application/json")
	public Customer getBorrower() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer borrower = customerDAO.findById(borrowerID);
			SessionManager.getSession().getTransaction().commit();
			return borrower;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
	
	@Post("application/json")
	public Customer updateCustomer(Customer borrower)
			throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO borrowerDAO = daoFactory.getCustomerDAO();
			Customer updatedCustomer = borrowerDAO.update(borrower);
			SessionManager.getSession().getTransaction().commit();
			return updatedCustomer;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteCustomer() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO borrowerDAO = daoFactory.getCustomerDAO();
			Customer borrower = borrowerDAO.findById(borrowerID);
			borrowerDAO.delete(borrower);
			SessionManager.getSession().getTransaction().commit();
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}
