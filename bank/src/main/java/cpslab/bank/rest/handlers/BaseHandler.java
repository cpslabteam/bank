package cpslab.bank.rest.handlers;

import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class BaseHandler {
	private Repository repository = RepositoryService.getInstance();

	protected void rollbackTransactionIfActive() {
		if (repository.isTransactionActive())
			repository.rollbackTransaction();
	}
}
