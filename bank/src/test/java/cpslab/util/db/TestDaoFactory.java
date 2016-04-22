package cpslab.util.db;

import org.junit.Assert;
import org.junit.Test;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.util.db.hibernate.BaseDataEntity;
import cpslab.util.db.hibernate.HibernateDAO;

public class TestDaoFactory {

    private static class TesterDataEntity extends
            BaseDataEntity {
        // We could add some fields there
    }

    private static interface TesterDaoForEntity
            extends GenericDAO<TesterDataEntity> {
        // could have methods to access MyDataEntity
    }

    private static class HibernateTesterDao extends
            HibernateDAO<TesterDataEntity>
            implements TesterDaoForEntity {
        /**
         * Default constructor is required.
         */
        public HibernateTesterDao() {
        }
        // should implement the methods declared in the interface MyDao
    }

    @Test
    public void registerDao() throws InstantiationException, IllegalAccessException {
//        HibernateDAOFactory.registerDao(MyDataEntity.class, HibernateMyDao.class);

//        MyDao dao = (MyDao) DAOFactory.create(MyDataEntity.class);
        
        AccountDAO dao = (AccountDAO) DAOFactory.create(Account.class);

        Assert.assertNotNull(dao);
    }

}
