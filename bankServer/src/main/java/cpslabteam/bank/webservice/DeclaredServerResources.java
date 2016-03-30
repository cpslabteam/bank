package cpslabteam.bank.webservice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.ServerResource;

import cpslabteam.bank.webservice.resources.AccountResource;
import cpslabteam.bank.webservice.resources.AccountsResource;
import cpslabteam.bank.webservice.resources.BorrowerResource;
import cpslabteam.bank.webservice.resources.BorrowersResource;
import cpslabteam.bank.webservice.resources.BranchResource;
import cpslabteam.bank.webservice.resources.BranchesResource;
import cpslabteam.bank.webservice.resources.CustomerResource;
import cpslabteam.bank.webservice.resources.CustomersResource;
import cpslabteam.bank.webservice.resources.DepositorResource;
import cpslabteam.bank.webservice.resources.DepositorsResource;
import cpslabteam.bank.webservice.resources.LoanResource;
import cpslabteam.bank.webservice.resources.LoansResource;

public final class DeclaredServerResources {

	private static Map<String, Class<? extends ServerResource>> serverResources;

	private static final String CUSTOMERS_BASE_PATH = "/customers";
	private static final String CUSTOMER_BASE_PATH = "/{customer}";
	private static final String DEPOSITORS_BASE_PATH = "/depositors";
	private static final String DEPOSITOR_BASE_PATH = "/{depositor}";
	private static final String BORROWERS_BASE_PATH = "/borrowers";
	private static final String BORROWER_BASE_PATH = "/{borrower}";
	private static final String BRANCHES_BASE_PATH = "/branches";
	private static final String BRANCH_BASE_PATH = "/{branch}";
	private static final String ACCOUNTS_BASE_PATH = "/accounts";
	private static final String ACCOUNT_BASE_PATH = "/{account}";
	private static final String LOANS_BASE_PATH = "/loans";
	private static final String LOAN_BASE_PATH = "/{loan}";

	static {
		serverResources = new LinkedHashMap<>();
		declareServerResources();
	}

	private static void declareServerResources() {
		serverResources.put(CUSTOMERS_BASE_PATH, CustomersResource.class);
		serverResources.put(CUSTOMERS_BASE_PATH + CUSTOMER_BASE_PATH, CustomerResource.class);
		serverResources.put(DEPOSITORS_BASE_PATH, DepositorsResource.class);
		serverResources.put(DEPOSITORS_BASE_PATH + DEPOSITOR_BASE_PATH, DepositorResource.class);
		serverResources.put(BORROWERS_BASE_PATH, BorrowersResource.class);
		serverResources.put(BORROWERS_BASE_PATH + BORROWER_BASE_PATH, BorrowerResource.class);
		serverResources.put(BRANCHES_BASE_PATH, BranchesResource.class);
		serverResources.put(BRANCHES_BASE_PATH + BRANCH_BASE_PATH, BranchResource.class);
		serverResources.put(ACCOUNTS_BASE_PATH, AccountsResource.class);
		serverResources.put(ACCOUNTS_BASE_PATH + ACCOUNT_BASE_PATH, AccountResource.class);
		serverResources.put(LOANS_BASE_PATH, LoansResource.class);
		serverResources.put(LOANS_BASE_PATH + LOAN_BASE_PATH, LoanResource.class);
	}

	public static Map<String, Class<? extends ServerResource>> getDeclaredServerResources() {
		return serverResources;
	}

	public static Set<String> getDeclaredServerResourcesURLs() {
		return serverResources.keySet();
	}
}
