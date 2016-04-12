package cpslabteam.bank.database.transaction;

public abstract class DatabaseTransactionManager {

	public static DatabaseTransaction getDatabaseTransaction(){
		return new DatabaseTransactionHibernate();
	}
	
	public static DatabaseTransaction beginDatabaseTransaction(){
		DatabaseTransaction transaction = new DatabaseTransactionHibernate();
		transaction.begin();
		return transaction;
	}
}
