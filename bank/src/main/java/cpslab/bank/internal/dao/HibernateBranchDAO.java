package cpslab.bank.internal.dao;

import java.util.List;

import org.hibernate.Query;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.util.db.hibernate.HibernateDao;

public class HibernateBranchDAO extends
        HibernateDao<Branch>
        implements BranchDAO {
	
	private final static String NAME_QUERY = "SELECT b FROM Branch b WHERE name = :name";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> findByName(String name) {
		Query query = getSession().createQuery(NAME_QUERY);
		query.setString("name", name);
		return query.list();
	}

}
