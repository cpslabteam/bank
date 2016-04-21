package cpslab.util.db;

import cpslab.util.db.hibernate.HibernateDatabaseTransaction;

public final class DatabaseTransactionManager {

    private DatabaseTransactionManager() {
    }

    public static DatabaseTransaction getTransaction() {
        return new HibernateDatabaseTransaction();
    }
}
