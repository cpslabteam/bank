package main.java.cpslabteam.bank;

import java.math.BigDecimal;

import org.hibernate.Session;

import main.java.cpslabteam.bank.database.objects.Branch;
import main.java.cpslabteam.bank.database.utils.SessionManager;

public class DatabaseTestRunner {

	public static void main(String[] args) {
		Session session = SessionManager.getSession();
		session.beginTransaction();
		Branch branch = new Branch();
		branch.setAssets(new BigDecimal(20));
		branch.setCity("TestCity");
		branch.setName("TestName");
		session.saveOrUpdate(branch);
		session.getTransaction().commit();
	}
}
