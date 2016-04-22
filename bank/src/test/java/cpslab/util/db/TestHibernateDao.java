package cpslab.util.db;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import cpslab.util.db.hibernate.BaseDataEntity;
import cpslab.util.db.hibernate.HibernateDAO;

public class TestHibernateDao {

    private static class TesterEntity extends
            BaseDataEntity {
        // We could add some fields there
        private String name;

        public TesterEntity() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static interface TesterDaoForEntity
            extends GenericDAO<TesterEntity> {
        // could have methods to access MyDataEntity
    }

    private static class HibernateTesterDao extends
            HibernateDAO<TesterEntity>
            implements TesterDaoForEntity {
        /**
         * Default constructor is required.
         */
        public HibernateTesterDao() {
        }
    }
    
    @BeforeClass
    public static void beforeClass() {
        //HibernateUtil.setTestMode(true);
    }

    @Test
    public void testCreate() {
        HibernateTesterDao dao = new HibernateTesterDao();

        TesterEntity e = new TesterEntity();
        e.setName("Some Name");
        dao.persist(e);
        
        //
    }

}
