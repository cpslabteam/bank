package cpslabteam.bank.database.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import cpslabteam.bank.database.objects.Customer;

public class CustomerDAOHibernate extends GenericHibernateDAO<Customer, Long>implements CustomerDAO {

	@Override
	public List<Customer> findDepositors() {
		return findByCriteria(Restrictions.isNotEmpty("accounts"));
	}

	@Override
	public List<Customer> findBorrowers() {
		return findByCriteria(Restrictions.isNotEmpty("loans"));
	}

}
