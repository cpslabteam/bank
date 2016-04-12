package cpslabteam.bank.webservice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.ServerResource;

import cpslabteam.bank.webservice.resources.account.AccountResource;
import cpslabteam.bank.webservice.resources.account.AccountsResource;
import cpslabteam.bank.webservice.resources.borrower.BorrowerResource;
import cpslabteam.bank.webservice.resources.borrower.BorrowersResource;
import cpslabteam.bank.webservice.resources.branch.BranchAccountResource;
import cpslabteam.bank.webservice.resources.branch.BranchAccountsResource;
import cpslabteam.bank.webservice.resources.branch.BranchLoanResource;
import cpslabteam.bank.webservice.resources.branch.BranchLoansResource;
import cpslabteam.bank.webservice.resources.branch.BranchResource;
import cpslabteam.bank.webservice.resources.branch.BranchesResource;
import cpslabteam.bank.webservice.resources.customer.CustomerAccountResource;
import cpslabteam.bank.webservice.resources.customer.CustomerAccountsResource;
import cpslabteam.bank.webservice.resources.customer.CustomerResource;
import cpslabteam.bank.webservice.resources.customer.CustomersResource;
import cpslabteam.bank.webservice.resources.depositor.DepositorResource;
import cpslabteam.bank.webservice.resources.depositor.DepositorsResource;
import cpslabteam.bank.webservice.resources.loan.LoanResource;
import cpslabteam.bank.webservice.resources.loan.LoansResource;

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
		serverResources.put(BRANCHES_BASE_PATH, BranchesResource.class);
		serverResources.put(BRANCHES_BASE_PATH + BRANCH_BASE_PATH, BranchResource.class);
		serverResources.put(BRANCHES_BASE_PATH + BRANCH_BASE_PATH + ACCOUNTS_BASE_PATH, BranchAccountsResource.class);
		serverResources.put(BRANCHES_BASE_PATH + BRANCH_BASE_PATH + ACCOUNTS_BASE_PATH + ACCOUNT_BASE_PATH,
				BranchAccountResource.class);
		serverResources.put(BRANCHES_BASE_PATH + BRANCH_BASE_PATH + LOANS_BASE_PATH, BranchLoansResource.class);
		serverResources.put(BRANCHES_BASE_PATH + BRANCH_BASE_PATH + LOANS_BASE_PATH + LOAN_BASE_PATH,
				BranchLoanResource.class);

		serverResources.put(ACCOUNTS_BASE_PATH, AccountsResource.class);
		serverResources.put(ACCOUNTS_BASE_PATH + ACCOUNT_BASE_PATH, AccountResource.class);

		serverResources.put(LOANS_BASE_PATH, LoansResource.class);
		serverResources.put(LOANS_BASE_PATH + LOAN_BASE_PATH, LoanResource.class);

		serverResources.put(CUSTOMERS_BASE_PATH, CustomersResource.class);
		serverResources.put(CUSTOMERS_BASE_PATH + CUSTOMER_BASE_PATH, CustomerResource.class);
		serverResources.put(CUSTOMERS_BASE_PATH + CUSTOMER_BASE_PATH + ACCOUNTS_BASE_PATH,
				CustomerAccountsResource.class);
		serverResources.put(CUSTOMERS_BASE_PATH + CUSTOMER_BASE_PATH + ACCOUNTS_BASE_PATH + ACCOUNT_BASE_PATH,
				CustomerAccountResource.class);

		serverResources.put(DEPOSITORS_BASE_PATH, DepositorsResource.class);
		serverResources.put(DEPOSITORS_BASE_PATH + DEPOSITOR_BASE_PATH, DepositorResource.class);

		serverResources.put(BORROWERS_BASE_PATH, BorrowersResource.class);
		serverResources.put(BORROWERS_BASE_PATH + BORROWER_BASE_PATH, BorrowerResource.class);
	}

	public static Map<String, Class<? extends ServerResource>> getDeclaredServerResources() {
		return serverResources;
	}

	public static Set<String> getDeclaredServerResourcesURLs() {
		return serverResources.keySet();
	}
}
