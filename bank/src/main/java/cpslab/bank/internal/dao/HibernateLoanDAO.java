package cpslab.bank.internal.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;

import cpslab.bank.api.dao.LoanDAO;
import cpslab.bank.api.entities.Loan;
import cpslab.util.db.hibernate.HibernateDao;

public class HibernateLoanDAO extends
        HibernateDao<Loan>
        implements LoanDAO {

    private final static String BRANCH_LOANS_QUERY =
        "SELECT ac FROM Branch b JOIN b.loans ac WHERE b.id = :id";
    private final static String BRANCH_LOAN_QUERY =
        "SELECT ac FROM Branch b JOIN b.loans ac WHERE b.id = :branch_id AND ac.id = :loan_id";
    private final static String CUSTOMER_LOANS_QUERY =
        "SELECT l FROM Customer c JOIN c.loans l WHERE c.id = :id";
    private final static String CUSTOMER_LOAN_QUERY =
        "SELECT l FROM Customer c JOIN c.loans l WHERE c.id = :customer_id AND l.id = :loan_id";

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
            throw new ObjectNotFoundException(loanID, getPersistentClass().getSimpleName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Loan> findCustomerLoans(Long customerID) {
        Query query = getSession().createQuery(CUSTOMER_LOANS_QUERY);
        query.setLong("id", customerID);
        return query.list();
    }

    @Override
    public Loan findCustomerLoan(Long customerID, Long loanID) {
        Query query = getSession().createQuery(CUSTOMER_LOAN_QUERY);
        query.setLong("customer_id", customerID);
        query.setLong("loan_id", loanID);
        Loan loan = (Loan) query.uniqueResult();
        if (loan != null) {
            return loan;
        } else {
            throw new ObjectNotFoundException(loanID, getPersistentClass().getSimpleName());
        }
    }

}
