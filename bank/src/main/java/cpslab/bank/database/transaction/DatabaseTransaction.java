package cpslab.bank.database.transaction;

public interface DatabaseTransaction {

	public void begin();
	
	public void commit();
	
	public void rollback();
	
	public boolean canRollback();
}
