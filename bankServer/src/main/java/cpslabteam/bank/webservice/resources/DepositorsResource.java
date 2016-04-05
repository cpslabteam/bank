package cpslabteam.bank.webservice.resources;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.dao.DepositorDAO;
import cpslabteam.bank.database.objects.Depositor;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

public class DepositorsResource extends ServerResource {

	@Get("application/json")
	public String getDepositors(Representation entity)
			throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			DepositorDAO depositorDAO = daoFactory.getDepositorDAO();
			List<Depositor> depositors = depositorDAO.findAll();
			SessionManager.getSession().getTransaction().commit();
			return BankJsonSerializer.serialize(depositors);
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}
