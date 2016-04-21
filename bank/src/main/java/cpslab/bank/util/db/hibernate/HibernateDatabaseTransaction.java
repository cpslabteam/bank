package cpslab.bank.util.db.hibernate;

import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.bank.util.db.DatabaseTransaction;

public class HibernateDatabaseTransaction implements DatabaseTransaction {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Transaction transaction = null;

	public HibernateDatabaseTransaction() {
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
