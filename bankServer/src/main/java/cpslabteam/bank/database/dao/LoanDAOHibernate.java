package cpslabteam.bank.database.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;

import cpslabteam.bank.database.objects.Loan;

public class LoanDAOHibernate extends GenericHibernateDAO<Loan, Long>implements LoanDAO {

	private final static String BRANCH_LOANS_QUERY = "SELECT ac FROM Branch b JOIN b.loans ac WHERE b.id = :id";
	private final static String BRANCH_LOAN_QUERY = "SELECT ac FROM Branch b JOIN b.loans ac WHERE b.id = :branch_id AND ac.id = :loan_id";

	@SuppressWarnings("unchecked")
	@Override
	public List<Loan> findBranchLoans(Long branchID) {
		Query query = getSession().createQuery(BRANCH_LOANS_QUERY);
		query.setLong("id", branchID);
		return query.list();
	}

	@Override
	public Loan findBranchLoan(Long branchID, Long loanID) {
		Query query = getSession().createQuery(BRANCH_LOAN_QUERY);
		query.setLong("branch_id", branchID);
		query.setLong("loan_id", loanID);
		Loan loan = (Loan) query.uniqueResult();
		if (loan != null) {
			return loan;
		} else {
			throw new ObjectNotFoundException(loanID, getPersistentClass().getName());
		}
	}

}
