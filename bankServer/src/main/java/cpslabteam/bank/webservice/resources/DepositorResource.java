package cpslabteam.bank.webservice.resources;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.dao.DepositorDAO;
import cpslabteam.bank.database.objects.Depositor;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

public class DepositorResource extends ServerResource {

	private String depositorID;

	@Override
	protected void doInit() throws ResourceException {
		depositorID = getAttribute("depositor");
	}

	@Get("application/json")
	public String doGet() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			DepositorDAO depositorDAO = daoFactory.getDepositorDAO();
			Depositor depositor = depositorDAO.findById(Long.valueOf(depositorID));
			String response = BankJsonSerializer.serialize(depositor);
			SessionManager.getSession().getTransaction().commit();
			return response;
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}
