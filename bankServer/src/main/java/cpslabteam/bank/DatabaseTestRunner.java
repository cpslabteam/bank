package cpslabteam.bank;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import cpslabteam.bank.database.dao.BranchDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Account;
import cpslabteam.bank.database.objects.Branch;
import cpslabteam.bank.database.objects.Depositor;
import cpslabteam.bank.database.utils.SessionManager;
import cpslabteam.bank.jsonserialization.JSONViews;

public class DatabaseTestRunner {

	public static void main(String[] args) throws IOException {
		SessionManager.getSession().beginTransaction();
		DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
		BranchDAO branchDAO = daoFactory.getBranchDAO();
		Branch branch = new Branch();
		branch.setAssets(new BigDecimal(20));
		branch.setCity("TestCity");
		branch.setName("TestName");
		branchDAO.makePersistent(branch);
		Account account = new Account();
		account.setAccountNumber("A-111");
		account.setBalance(new BigDecimal(20));
		account.setBranch(branch);
		daoFactory.getAccountDAO().makePersistent(account);
		Depositor depositor = new Depositor();
		depositor.setCity("TestCity");
		depositor.setStreet("TestStreet");
		depositor.setName("TestName");
		depositor.getAccounts().add(account);
		daoFactory.getDepositorDAO().makePersistent(depositor);
		SessionManager.getSession().getTransaction().commit();
		
		ObjectWriter objectWriter = new ObjectMapper().writerWithView(JSONViews.Details.class).withDefaultPrettyPrinter();
		System.out.println(objectWriter.writeValueAsString(depositor));
		System.out.println(objectWriter.writeValueAsString(account));
		System.out.println(objectWriter.writeValueAsString(branch));
		
		
	}
}
