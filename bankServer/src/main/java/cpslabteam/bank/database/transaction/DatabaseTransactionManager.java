package cpslabteam.bank.database.transaction;

public abstract class DatabaseTransactionManager {

	public static DatabaseTransaction getDatabaseTransaction(){
		return new DatabaseTransactionHibernate();
	}
}
