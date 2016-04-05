package cpslabteam.bank.webservice.resources;

import org.hibernate.HibernateException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;

import cpslabteam.bank.database.SessionManager;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.dao.LoanDAO;
import cpslabteam.bank.database.objects.Loan;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

public class LoanResource extends ServerResource {

	private String loanID;

	@Override
	protected void doInit() throws ResourceException {
		loanID = getAttribute("loan");
	}

	@Get("application/json")
	public String getLoan() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findById(Long.valueOf(loanID));
			SessionManager.getSession().getTransaction().commit();
			return BankJsonSerializer.serialize(loan);
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}

	@Post("application/json")
	public String updateLoan(Loan loan) throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan updatedLoan = loanDAO.update(loan);
			SessionManager.getSession().getTransaction().commit();
			return BankJsonSerializer.serialize(updatedLoan);
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}

	@Delete("application/json")
	public void deleteLoan() throws InterruptedException, JsonProcessingException, HibernateException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			LoanDAO loanDAO = daoFactory.getLoanDAO();
			Loan loan = loanDAO.findById(Long.valueOf(loanID));
			loanDAO.makeTransient(loan);
			SessionManager.getSession().getTransaction().commit();
		} catch (Exception e) {
			if (SessionManager.getSession().getTransaction().getStatus().canRollback())
				SessionManager.getSession().getTransaction().rollback();
			throw e;
		}
	}
}
