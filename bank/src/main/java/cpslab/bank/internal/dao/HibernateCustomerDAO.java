package cpslab.bank.internal.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import cpslab.bank.api.dao.CustomerDAO;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.util.db.hibernate.HibernateDAO;

public class HibernateCustomerDAO extends HibernateDAO<Customer>implements CustomerDAO {

	private final static String ACCOUNT_OWNERS_QUERY = "SELECT c FROM Account ac JOIN ac.owners c WHERE ac.id = :id";
	private final static String LOAN_OWNERS_QUERY = "SELECT c FROM Loan l JOIN l.owners c WHERE l.id = :id";
	private final static String ACCOUNT_OWNER_QUERY = "SELECT c FROM Account ac JOIN ac.owners c WHERE ac.id = :account_id AND c.id = :owner_id";
	private final static String LOAN_OWNER_QUERY = "SELECT c FROM Loan l JOIN l.owners c WHERE l.id = :loan_id AND c.id = :owner_id";

	@Override
	public List<Customer> findDepositors() {
		return findByCriteria(Restrictions.isNotEmpty("accounts"));
	}

	@Override
	public List<Customer> findBorrowers() {
		return findByCriteria(Restrictions.isNotEmpty("loans"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findAccountOwners(Long accountID) {
		Query query = getSession().createQuery(ACCOUNT_OWNERS_QUERY);
		query.setLong("id", accountID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findLoanOwners(Long loanID) {
		Query query = getSession().createQuery(LOAN_OWNERS_QUERY);
		query.setLong("id", loanID);
		return query.list();
	}

	@Override
	public Customer findAccountOwner(Long accountID, Long ownerID) {
		Query query = getSession().createQuery(ACCOUNT_OWNER_QUERY);
		query.setLong("account_id", accountID);
		query.setLong("owner_id", ownerID);
		Customer owner = (Customer) query.uniqueResult();
		if (owner != null) {
			return owner;
		} else {
			throw new ObjectNotFoundException(ownerID, getPersistentClass().getName());
		}
	}

	@Override
	public Customer findLoanOwner(Long loanID, Long ownerID) {
		Query query = getSession().createQuery(LOAN_OWNER_QUERY);
		query.setLong("loan_id", loanID);
		query.setLong("owner_id", ownerID);
		Customer owner = (Customer) query.uniqueResult();
		if (owner != null) {
			return owner;
		} else {
			throw new ObjectNotFoundException(ownerID, getPersistentClass().getName());
		}
	}

}
