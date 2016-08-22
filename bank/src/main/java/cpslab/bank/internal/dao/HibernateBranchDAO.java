package cpslab.bank.internal.dao;

import java.util.List;

import org.hibernate.Query;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.util.db.hibernate.HibernateDao;

public class HibernateBranchDAO extends HibernateDao<Branch>implements BranchDAO {

	private final static String NAME_QUERY = "SELECT b FROM Branch b WHERE name = :name";

	private final static String DIVISION_BRANCHES_QUERY =
			"SELECT b FROM Division d JOIN d.branches b WHERE d.id = :id";

	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> findByName(String name) {
		Query query = getSession().createQuery(NAME_QUERY);
		query.setString("name", name);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> findDivisionBranches(Long divisionID) {
		Query query = getSession().createQuery(DIVISION_BRANCHES_QUERY);
		query.setLong("id", divisionID);
		return query.list();
	}

}
