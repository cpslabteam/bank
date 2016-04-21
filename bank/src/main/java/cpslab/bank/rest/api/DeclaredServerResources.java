package cpslab.bank.rest.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.ServerResource;

import cpslab.bank.rest.borrower.BorrowerResource;
import cpslab.bank.rest.borrower.BorrowersResource;
import cpslab.bank.rest.services.account.AccountBranchResource;
import cpslab.bank.rest.services.account.AccountOwnerResource;
import cpslab.bank.rest.services.account.AccountOwnersResource;
import cpslab.bank.rest.services.account.AccountResource;
import cpslab.bank.rest.services.account.AccountsResource;
import cpslab.bank.rest.services.branch.BranchAccountResource;
import cpslab.bank.rest.services.branch.BranchAccountsResource;
import cpslab.bank.rest.services.branch.BranchLoanResource;
import cpslab.bank.rest.services.branch.BranchLoansResource;
import cpslab.bank.rest.services.branch.BranchResource;
import cpslab.bank.rest.services.branch.BranchesResource;
import cpslab.bank.rest.services.customer.CustomerAccountDepositResource;
import cpslab.bank.rest.services.customer.CustomerAccountResource;
import cpslab.bank.rest.services.customer.CustomerAccountWithdrawResource;
import cpslab.bank.rest.services.customer.CustomerAccountsResource;
import cpslab.bank.rest.services.customer.CustomerLoanDepositResource;
import cpslab.bank.rest.services.customer.CustomerLoanResource;
import cpslab.bank.rest.services.customer.CustomerLoansResource;
import cpslab.bank.rest.services.customer.CustomerResource;
import cpslab.bank.rest.services.customer.CustomersResource;
import cpslab.bank.rest.services.depositor.DepositorResource;
import cpslab.bank.rest.services.depositor.DepositorsResource;
import cpslab.bank.rest.services.loan.LoanBranchResource;
import cpslab.bank.rest.services.loan.LoanOwnerResource;
import cpslab.bank.rest.services.loan.LoanOwnersResource;
import cpslab.bank.rest.services.loan.LoanResource;
import cpslab.bank.rest.services.loan.LoansResource;

public final class DeclaredServerResources {

	private static Map<String, Class<? extends ServerResource>> serverResources;

	static {
		serverResources = new LinkedHashMap<>();
		declareServerResources();
	}

	private static void declareServerResources() {
		serverResources.put("/branches", BranchesResource.class);
		serverResources.put("/branches/{branch}", BranchResource.class);
		serverResources.put("/branches/{branch}/accounts", BranchAccountsResource.class);
		serverResources.put("/branches/{branch}/accounts/{account}", BranchAccountResource.class);
		serverResources.put("/branches/{branch}/loans", BranchLoansResource.class);
		serverResources.put("/branches/{branch}/loans/{loan}", BranchLoanResource.class);

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
		serverResources.put("/customers/{customer}/accounts/{account}/deposit", CustomerAccountDepositResource.class);
		serverResources.put("/customers/{customer}/accounts/{account}/withdraw", CustomerAccountWithdrawResource.class);
		serverResources.put("/customers/{customer}/loans", CustomerLoansResource.class);
		serverResources.put("/customers/{customer}/loans/{loan}", CustomerLoanResource.class);
		serverResources.put("/customers/{customer}/loans/{loan}/deposit", CustomerLoanDepositResource.class);

		serverResources.put("/depositors", DepositorsResource.class);
		serverResources.put("/depositors/{depositor}", DepositorResource.class);

		serverResources.put("/borrowers", BorrowersResource.class);
		serverResources.put("/borrowers/{borrower}", BorrowerResource.class);
	}

	public static Map<String, Class<? extends ServerResource>> getDeclaredServerResources() {
		return serverResources;
	}

	public static Set<String> getDeclaredServerResourcesURLs() {
		return serverResources.keySet();
	}
}
