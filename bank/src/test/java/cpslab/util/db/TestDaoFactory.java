package cpslab.util.db;

import org.junit.Assert;
import org.junit.Test;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.util.db.hibernate.HibernateDao;
import cpslab.util.db.spi.BaseDataEntity;

public class TestDaoFactory {

    private static class TesterDataEntity extends
            BaseDataEntity {
        // We could add some fields there
    }

    private static interface TesterDaoForEntity
            extends Dao<TesterDataEntity> {
        // could have methods to access MyDataEntity
    }

    private static class HibernateTesterDao extends
            HibernateDao<TesterDataEntity>
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
        
        AccountDAO dao = (AccountDAO) r.createDao(Account.class);

        Assert.assertNotNull(dao);
    }

}
