package cpslab.bank.rest.api;

import java.util.LinkedHashMap;
import java.util.Map;

import cpslab.bank.rest.services.LoginResource;
import cpslab.bank.rest.services.account.AccountBranchResource;
import cpslab.bank.rest.services.account.AccountOwnerResource;
import cpslab.bank.rest.services.account.AccountOwnersResource;
import cpslab.bank.rest.services.account.AccountResource;
import cpslab.bank.rest.services.account.AccountsResource;
import cpslab.bank.rest.services.branch.BranchAccountsResource;
import cpslab.bank.rest.services.branch.BranchLoansResource;
import cpslab.bank.rest.services.branch.BranchResource;
import cpslab.bank.rest.services.branch.BranchesResource;
import cpslab.bank.rest.services.customer.CustomerAccountResource;
import cpslab.bank.rest.services.customer.CustomerAccountsResource;
import cpslab.bank.rest.services.customer.CustomerLoanResource;
import cpslab.bank.rest.services.customer.CustomerLoansResource;
import cpslab.bank.rest.services.customer.CustomerResource;
import cpslab.bank.rest.services.customer.CustomersResource;
import cpslab.bank.rest.services.loan.LoanBranchResource;
import cpslab.bank.rest.services.loan.LoanOwnerResource;
import cpslab.bank.rest.services.loan.LoanOwnersResource;
import cpslab.bank.rest.services.loan.LoanResource;
import cpslab.bank.rest.services.loan.LoansResource;
import cpslab.util.rest.RestService;

public final class DeclaredServerResources {

	private static Map<String, Class<? extends RestService>> serverResources;

	static {
		serverResources = new LinkedHashMap<>();
		declareServerResources();
	}

	private static void declareServerResources() {
		serverResources.put("/branches", BranchesResource.class);
		serverResources.put("/branches/{branch}", BranchResource.class);
		serverResources.put("/branches/{branch}/accounts", BranchAccountsResource.class);
		serverResources.put("/branches/{branch}/loans", BranchLoansResource.class);

		serverResources.put("/accounts", AccountsResource.class);
		serverResources.put("/accounts/{account}", AccountResource.class);
		serverResources.put("/accounts/{account}/branch", AccountBranchResource.class);
		serverResources.put("/accounts/{account}/owners", AccountOwnersResource.class);
		serverResources.put("/accounts/{account}/owners/{owner}", AccountOwnerResource.class);

		serverResources.put("/loans", LoansResource.class);
		serverResources.put("/loans/{loan}", LoanResource.class);
		serverResources.put("/loans/{loan}/branch", LoanBranchResource.class);
		serverResources.put("/loans/{loan}/owners", LoanOwnersResource.class);
		serverResources.put("/loans/{loan}/owners/{owner}", LoanOwnerResource.class);

		serverResources.put("/customers", CustomersResource.class);
		serverResources.put("/customers/{customer}", CustomerResource.class);
		serverResources.put("/customers/{customer}/accounts", CustomerAccountsResource.class);
		serverResources.put("/customers/{customer}/accounts/{account}", CustomerAccountResource.class);
		serverResources.put("/customers/{customer}/loans", CustomerLoansResource.class);
		serverResources.put("/customers/{customer}/loans/{loan}", CustomerLoanResource.class);
		
		serverResources.put("/login", LoginResource.class);
	}

	public static Map<String, Class<? extends RestService>> getDeclaredServerResources() {
		return serverResources;
	}
}
