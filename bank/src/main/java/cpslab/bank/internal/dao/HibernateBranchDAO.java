package cpslab.bank.internal.dao;

import cpslab.bank.api.dao.BranchDAO;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.util.db.hibernate.HibernateDAO;

public class HibernateBranchDAO extends
        HibernateDAO<Branch>
        implements BranchDAO {

}
