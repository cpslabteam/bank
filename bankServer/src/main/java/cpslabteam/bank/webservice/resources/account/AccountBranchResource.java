package cpslabteam.bank.webservice.resources.account;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.AccountDAO;
import cpslabteam.bank.database.dao.BranchDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.database.objects.Branch;
import cpslabteam.bank.database.transaction.DatabaseTransaction;
import cpslabteam.bank.database.transaction.DatabaseTransactionManager;

public class AccountBranchResource extends ServerResource {

	private Long accountID;

	@Override
	protected void doInit() throws ResourceException {
		accountID = Long.valueOf(getAttribute("account"));
	}

	@Get("application/json")
	public Branch getBranch() throws InterruptedException, IOException, HibernateException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			Account account = accountDAO.findById(accountID);
			Branch branch = account.getBranch();
			transaction.commit();
			return branch;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

	@Put("application/json")
	public Branch changeBranch(Representation entity)
			throws InterruptedException, IOException, HibernateException, JSONException {
		DatabaseTransaction transaction = DatabaseTransactionManager.getDatabaseTransaction();
		try {
			JSONObject request = new JSONObject(entity.getText());
			String branchID = request.getString("id");
			transaction.begin();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			AccountDAO accountDAO = daoFactory.getAccountDAO();
			BranchDAO branchDAO = daoFactory.getBranchDAO();
			Account account = accountDAO.findById(accountID);
			Branch branch = branchDAO.findById(Long.valueOf(branchID));
			account.setBranch(branch);
			accountDAO.update(account);
			transaction.commit();
			return branch;
		} catch (Exception e) {
			if (transaction.canRollback())
				transaction.rollback();
			throw e;
		}
	}

}
