package cpslab.bank.rest.services;

import org.json.JSONObject;

import cpslab.bank.api.dao.UserAccountDAO;
import cpslab.bank.api.entities.UserAccount;
import cpslab.util.rest.services.JsonPostService;

public class LoginResource extends BaseResource implements JsonPostService{
	
	@Override
	public String handlePost(JSONObject requestParams) throws Throwable {
		long transactionId = getRepository().openTransaction();
		try {
			UserAccountDAO userAccountDAO = (UserAccountDAO) getRepository().createDao(UserAccount.class, transactionId);
			String username = requestParams.getString("username");
			String password = requestParams.getString("password");
			boolean isAuthorized = userAccountDAO.hasUserPassword(username, password);
			return new JSONObject().put("success", isAuthorized).toString();
		} catch (Exception e) {
			rollbackTransactionIfActive(transactionId);
			throw e;
		}
	}

}
