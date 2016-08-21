package cpslab.bank.rest.utils;

import org.restlet.security.SecretVerifier;

import cpslab.bank.api.dao.UserAccountDAO;
import cpslab.bank.api.entities.UserAccount;
import cpslab.util.db.Repository;
import cpslab.util.db.RepositoryService;

public class UserVerifier extends SecretVerifier {

    public int verify(String identifier, char[] secret) {
    	Repository repository = RepositoryService.getInstance();
    	long transactionId = repository.openTransaction();
    	UserAccountDAO userDAO = (UserAccountDAO) repository.createDao(UserAccount.class, transactionId);
    	return userDAO.hasUserPassword(identifier, new String(secret)) ? RESULT_VALID : RESULT_INVALID;
    }
}
