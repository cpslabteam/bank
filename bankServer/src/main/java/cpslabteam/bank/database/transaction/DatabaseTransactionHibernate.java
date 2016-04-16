package cpslabteam.bank.database.transaction;

import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslabteam.bank.database.SessionManager;

public class DatabaseTransactionHibernate implements DatabaseTransaction {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Transaction transaction = null;

	public DatabaseTransactionHibernate() {
		super();
		this.transaction = SessionManager.getSession().getTransaction();
	}

	@Override
	public void begin() {
		logger.debug("Database Transaction started");
		transaction.begin();
	}

	@Override
	public void commit() {
		logger.debug("Database Transaction commited");
		transaction.commit();
	}

	@Override
	public void rollback() {
		logger.debug("Database Transaction rolled back");
		transaction.rollback();
	}

	@Override
	public boolean canRollback() {
		return transaction.getStatus().canRollback();
	}

}
