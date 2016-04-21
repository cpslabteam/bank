package cpslab.bank.util.db;

public interface DatabaseTransaction {

	public void begin();
	
	public void commit();
	
	public void rollback();
	
	public boolean canRollback();
}
