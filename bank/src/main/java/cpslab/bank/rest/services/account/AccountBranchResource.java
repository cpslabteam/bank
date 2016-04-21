package cpslab.bank.rest.services.account;

import java.io.IOException;
import java.io.Serializable;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.internal.dao.HibernateAccountDAO;
import cpslab.bank.internal.dao.HibernateDAOFactory;
import cpslab.util.db.DatabaseTransaction;
import cpslab.util.db.DatabaseTransactionManager;
import cpslab.util.db.GenericDAO;

public class AccountBranchResource extends
        ServerResource {

    private Long accountID;

    @Override
    protected void doInit() throws ResourceException {
        accountID = Long.valueOf(getAttribute("account"));
    }

    @Get("application/json")
    public Branch getBranch() throws InterruptedException, IOException, HibernateException {
        DatabaseTransaction tx = DatabaseTransactionManager.getTransaction();
        try {
            tx.begin();
            AccountDAO accountDAO = (AccountDAO) DAOFactory.createDao(Account.class);
            Account account = accountDAO.findById(accountID);
            Branch branch = account.getBranch();
            tx.commit();
            return branch;
        } catch (Exception e) {
            if (tx.canRollback())
                tx.rollback();
            throw e;
        }
    }

    @Put("application/json")
    public Branch changeBranch(Representation entity)
            throws InterruptedException, IOException, HibernateException, JSONException {
        DatabaseTransaction transaction = DatabaseTransactionManager.getTransaction();
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
