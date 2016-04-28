package cpslab.bank.internal.dao;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.util.db.hibernate.HibernateDao;

public class HibernateBranchDAO extends
        HibernateDao<Branch>
        implements BranchDAO {

}
