package cpslab.bank.util.db;

import cpslab.bank.util.db.hibernate.HibernateDatabaseTransaction;

public final class DatabaseTransactionManager {

    private DatabaseTransactionManager() {
    }

    public static DatabaseTransaction getTransaction() {
        return new HibernateDatabaseTransaction();
    }
}
