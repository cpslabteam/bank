package cpslabteam.bank.webservice.resources;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.BranchDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Branch;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

public class BranchResource extends ServerResource {

	private String branchID;

	@Override
	protected void doInit() throws ResourceException {
		branchID = getAttribute("branch");
	}

	@Get("application/json")
	public String doGet() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			String response = BankJsonSerializer.serializeDetails(branch);
			SessionManager.getSession().getTransaction().commit();
			return response;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}