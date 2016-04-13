package cpslabteam.bank.webservice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.ServerResource;

import cpslabteam.bank.webservice.resources.account.AccountBranchResource;
import cpslabteam.bank.webservice.resources.account.AccountOwnerResource;
import cpslabteam.bank.webservice.resources.account.AccountOwnersResource;
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

	private static final String CUSTOMERS_PATH = "/customers";
	private static final String CUSTOMER_PATH = "/{customer}";
	private static final String DEPOSITORS_PATH = "/depositors";
	private static final String DEPOSITOR_PATH = "/{depositor}";
	private static final String BORROWERS_PATH = "/borrowers";
	private static final String BORROWER_PATH = "/{borrower}";
	private static final String BRANCHES_PATH = "/branches";
	private static final String BRANCH_PATH = "/{branch}";
	private static final String ACCOUNTS_PATH = "/accounts";
	private static final String ACCOUNT_PATH = "/{account}";
	private static final String LOANS_PATH = "/loans";
	private static final String LOAN_PATH = "/{loan}";
	private static final String ACCOUNT_BRANCH_PATH = "/branch";
	private static final String ACCOUNT_OWNERS_PATH = "/owners";
	private static final String ACCOUNT_OWNER_PATH = "/{owner}";

	static {
		serverResources = new LinkedHashMap<>();
		declareServerResources();
	}

	private static void declareServerResources() {
		serverResources.put(BRANCHES_PATH, BranchesResource.class);
		serverResources.put(BRANCHES_PATH + BRANCH_PATH, BranchResource.class);
		serverResources.put(BRANCHES_PATH + BRANCH_PATH + ACCOUNTS_PATH, BranchAccountsResource.class);
		serverResources.put(BRANCHES_PATH + BRANCH_PATH + ACCOUNTS_PATH + ACCOUNT_PATH, BranchAccountResource.class);
		serverResources.put(BRANCHES_PATH + BRANCH_PATH + LOANS_PATH, BranchLoansResource.class);
		serverResources.put(BRANCHES_PATH + BRANCH_PATH + LOANS_PATH + LOAN_PATH, BranchLoanResource.class);

		serverResources.put(ACCOUNTS_PATH, AccountsResource.class);
		serverResources.put(ACCOUNTS_PATH + ACCOUNT_PATH, AccountResource.class);
		serverResources.put(ACCOUNTS_PATH + ACCOUNT_PATH + ACCOUNT_BRANCH_PATH, AccountBranchResource.class);
		serverResources.put(ACCOUNTS_PATH + ACCOUNT_PATH + ACCOUNT_OWNERS_PATH, AccountOwnersResource.class);
		serverResources.put(ACCOUNTS_PATH + ACCOUNT_PATH + ACCOUNT_OWNERS_PATH + ACCOUNT_OWNER_PATH,
				AccountOwnerResource.class);

		serverResources.put(LOANS_PATH, LoansResource.class);
		serverResources.put(LOANS_PATH + LOAN_PATH, LoanResource.class);

		serverResources.put(CUSTOMERS_PATH, CustomersResource.class);
		serverResources.put(CUSTOMERS_PATH + CUSTOMER_PATH, CustomerResource.class);
		serverResources.put(CUSTOMERS_PATH + CUSTOMER_PATH + ACCOUNTS_PATH, CustomerAccountsResource.class);
		serverResources.put(CUSTOMERS_PATH + CUSTOMER_PATH + ACCOUNTS_PATH + ACCOUNT_PATH,
				CustomerAccountResource.class);

		serverResources.put(DEPOSITORS_PATH, DepositorsResource.class);
		serverResources.put(DEPOSITORS_PATH + DEPOSITOR_PATH, DepositorResource.class);

		serverResources.put(BORROWERS_PATH, BorrowersResource.class);
		serverResources.put(BORROWERS_PATH + BORROWER_PATH, BorrowerResource.class);
	}

	public static Map<String, Class<? extends ServerResource>> getDeclaredServerResources() {
		return serverResources;
	}

	public static Set<String> getDeclaredServerResourcesURLs() {
		return serverResources.keySet();
	}
}
