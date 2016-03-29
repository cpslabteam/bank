package cpslabteam.bank.webservice.resources;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.BorrowerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Borrower;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

public class BorrowerResource extends ServerResource {

	private String borrowerID;

	@Override
	protected void doInit() throws ResourceException {
		borrowerID = getAttribute("borrower");
	}

	@Get("application/json")
	public String doGet() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BorrowerDAO borrowerDAO = daoFactory.getBorrowerDAO();
			Borrower borrower = borrowerDAO.findById(Long.valueOf(borrowerID));
			String response = BankJsonSerializer.serializeDetails(borrower);
			SessionManager.getSession().getTransaction().commit();
			return response;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}
