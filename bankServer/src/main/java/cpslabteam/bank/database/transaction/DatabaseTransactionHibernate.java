package cpslabteam.bank.database.transaction;

import org.hibernate.Transaction;

import cpslabteam.bank.database.SessionManager;

public class DatabaseTransactionHibernate implements DatabaseTransaction {

	private Transaction transaction = null;

	public DatabaseTransactionHibernate() {
		super();
		this.transaction = SessionManager.getSession().getTransaction();
	}

	@Override
	public void begin() {
		transaction.begin();
	}

	@Override
	public void commit() {
		transaction.commit();
	}

	@Override
	public void rollback() {
		transaction.rollback();
	}

	@Override
	public boolean canRollback() {
		return transaction.getStatus().canRollback();
	}

}
