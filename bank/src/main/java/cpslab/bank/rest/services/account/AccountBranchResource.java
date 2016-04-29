package cpslab.bank.rest.services.account;

import java.io.IOException;

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
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class AccountBranchResource extends
        ServerResource {

    private Long accountID;

    @Override
    protected void doInit() throws ResourceException {
        accountID = Long.valueOf(getAttribute("account"));
    }

    @Get("application/json")
    public Branch getBranch() throws InterruptedException, IOException, HibernateException {
        Repository r = RepositoryService.getInstance();

        try {
            AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);

            r.openTransaction();
            Account account = accountDAO.findById(accountID);
            Branch branch = account.getBranch();
            r.closeTransaction();

            return branch;
        } catch (Exception e) {
            r.rollbackTransaction();
            throw e;
        }
    }

    @Put("application/json")
    public Branch changeBranch(Representation entity)
            throws InterruptedException, IOException, HibernateException, JSONException {
    	Repository r = RepositoryService.getInstance();
        try {
            JSONObject request = new JSONObject(entity.getText());
            String branchID = request.getString("id");
            AccountDAO accountDAO = (AccountDAO) r.createDao(Account.class);
            BranchDAO branchDAO = (BranchDAO) r.createDao(Branch.class);
            
            r.openTransaction();
            Account account = accountDAO.findById(accountID);
            Branch branch = branchDAO.findById(Long.valueOf(branchID));
            account.setBranch(branch);
            accountDAO.update(account);
            r.closeTransaction();
            return branch;
        } catch (Exception e) {
            r.rollbackTransaction();
            throw e;
        }
    }

}
