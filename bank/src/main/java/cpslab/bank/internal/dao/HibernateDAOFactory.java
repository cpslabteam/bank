package cpslab.bank.internal.dao;

import cpslab.bank.api.dao.DAOFactory;
import cpslab.bank.api.entities.Account;
import cpslab.bank.api.entities.Branch;
import cpslab.bank.api.entities.Customer;
import cpslab.bank.api.entities.Loan;

public class HibernateDAOFactory extends
        DAOFactory {

    static {
        addDaoFactory(Account.class, HibernateAccountDAO.class);
        addDaoFactory(Loan.class, HibernateLoanDAO.class);
        addDaoFactory(Branch.class, HibernateBranchDAO.class);
        addDaoFactory(Customer.class, HibernateCustomerDAO.class);
    }

}
