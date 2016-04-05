package cpslabteam.bank.webservice.resources;

import java.util.List;

import org.hibernate.HibernateException;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.BranchDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Branch;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

public class BranchesResource extends ServerResource {

	@Get("application/json")
	public String getBranches() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			List<Branch> branches = branchDAO.findAll();
			SessionManager.getSession().getTransaction().commit();
			return BankJsonSerializer.serialize(branches);
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}

	@Post("application/json")
	public String createBranch(Branch branch)
			throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Branch createdBranch = branchDAO.persist(branch);
			SessionManager.getSession().getTransaction().commit();
			return BankJsonSerializer.serialize(createdBranch);
		} catch (Exception e) {
			System.out.println(e);
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}
