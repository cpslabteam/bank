package cpslab.bank.internal.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;

import cpslab.bank.api.dao.AccountDAO;
import cpslab.bank.api.entities.Account;
import cpslab.util.db.hibernate.HibernateDAO;

public class HibernateAccountDAO extends
        HibernateDAO<Account>
        implements AccountDAO {

    private final static String CUSTOMER_ACCOUNTS_QUERY =
        "SELECT ac FROM Customer c JOIN c.accounts ac WHERE c.id = :id";

    private final static String BRANCH_ACCOUNTS_QUERY =
        "SELECT ac FROM Branch b JOIN b.accounts ac WHERE b.id = :id";

    private final static String CUSTOMER_ACCOUNT_QUERY =
        "SELECT ac FROM Customer c JOIN c.accounts ac WHERE c.id = :customer_id AND ac.id = :account_id";

    private final static String BRANCH_ACCOUNT_QUERY =
        "SELECT ac FROM Branch b JOIN b.accounts ac WHERE b.id = :branch_id AND ac.id = :account_id";

    /**
     * Make sure it can be instantiated thorugh reflection.
     */
    public HibernateAccountDAO() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Account> findCustomerAccounts(Long customerID) {
        Query query = getSession().createQuery(CUSTOMER_ACCOUNTS_QUERY);
        query.setLong("id", customerID);
        return query.list();
    }

    @Override
    public Account findCustomerAccount(Long customerID, Long accountID) {
        Query query = getSession().createQuery(CUSTOMER_ACCOUNT_QUERY);
        query.setLong("customer_id", customerID);
        query.setLong("account_id", accountID);
        Account account = (Account) query.uniqueResult();
        if (account != null) {
            return account;
        } else {
            throw new ObjectNotFoundException(accountID, getPersistentClass().getName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Account> findBranchAccounts(Long branchID) {
        Query query = getSession().createQuery(BRANCH_ACCOUNTS_QUERY);
        query.setLong("id", branchID);
        return query.list();
    }

    @Override
    public Account findBranchAccount(Long branchID, Long accountID) {
        Query query = getSession().createQuery(BRANCH_ACCOUNT_QUERY);
        query.setLong("branch_id", branchID);
        query.setLong("account_id", accountID);
        Account account = (Account) query.uniqueResult();
        if (account != null) {
            return account;
        } else {
            throw new ObjectNotFoundException(accountID, getPersistentClass().getName());
        }
    }

}
