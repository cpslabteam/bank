package cpslab.bank.internal.dao;

import org.junit.BeforeClass;
import org.junit.Test;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.util.db.hibernate.HibernateRepository;

public class TestHibernateAccountDAO {

    /**
     * Make sure that account DAO is in the DAO Factory
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        __DaoFactory.registerDao(Account.class, HibernateAccountDAO.class);
        
        HibernateRepository.getInstance().registerDao(Account.class, HibernateAccountDAO.class);
    }

    @Test
    public void test() {
        AccountDAO dao = (AccountDAO) r.createDao(Account.class);
        

    }

}
