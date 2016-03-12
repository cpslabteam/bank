package main.java.cpslabteam.bank;

import java.math.BigDecimal;

import main.java.cpslabteam.bank.database.dao.BranchDAO;
import main.java.cpslabteam.bank.database.dao.DAOFactory;
import main.java.cpslabteam.bank.database.objects.Branch;
import main.java.cpslabteam.bank.database.utils.SessionManager;

public class DatabaseTestRunner {

	public static void main(String[] args) {
//		Session session = SessionManager.getSessionFactory().openSession();
//		session.beginTransaction();
//		Branch branch = new Branch();
//		branch.setAssets(new BigDecimal(20));
//		branch.setCity("TestCity");
//		branch.setName("TestName");
//		session.saveOrUpdate(branch);
//		session.getTransaction().commit();
//		List<Branch> branches = (List<Branch>) session.createQuery("from Branch").list();
//		session.close();
		SessionManager.getSession().beginTransaction();
		DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
		BranchDAO branchDAO = daoFactory.getBranchDAO();
		Branch branch = new Branch();
		branch.setAssets(new BigDecimal(20));
		branch.setCity("TestCity");
		branch.setName("TestName");
		branchDAO.makePersistent(branch);
		SessionManager.getSession().getTransaction().commit();
	}
}
