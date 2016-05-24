package cpslab.bank.rest.services;

import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;
import cpslab.util.rest.RestJSONServicesProvider;

public class BaseResource extends RestJSONServicesProvider {

	private Repository repository = RepositoryService.getInstance();

	public Repository getRepository() {
		return repository;
	}

	public long getIdAttribute(String name) {
		return Long.valueOf(getAttribute(name));
	}

	public void rollbackTransactionIfActive(long id) {
		if (repository.isTransactionActive(id))
			repository.rollbackTransaction(id);
	}
}
