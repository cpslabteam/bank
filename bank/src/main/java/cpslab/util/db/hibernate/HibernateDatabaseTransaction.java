package cpslab.util.db.hibernate;

import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.util.db.DatabaseTransaction;

public class HibernateDatabaseTransaction implements DatabaseTransaction {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Transaction tx = null;

	public HibernateDatabaseTransaction() {
		super();
		this.tx = SessionManager.getSession().getTransaction();
	}

	@Override
	public void begin() {
		logger.debug("Database Transaction started");
		tx.begin();
	}

	@Override
	public void commit() {
		logger.debug("Database Transaction commited");
		tx.commit();
	}

	@Override
	public void rollback() {
		logger.debug("Database Transaction rolled back");
		tx.rollback();
	}

	@Override
	public boolean canRollback() {
		return tx.getStatus().canRollback();
	}

}
