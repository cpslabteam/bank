package cpslabteam.bank.server.restapi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.ServerResource;

import cpslabteam.bank.server.restapi.resources.account.AccountBranchResource;
import cpslabteam.bank.server.restapi.resources.account.AccountOwnerResource;
import cpslabteam.bank.server.restapi.resources.account.AccountOwnersResource;
import cpslabteam.bank.server.restapi.resources.account.AccountResource;
import cpslabteam.bank.server.restapi.resources.account.AccountsResource;
import cpslabteam.bank.server.restapi.resources.borrower.BorrowerResource;
import cpslabteam.bank.server.restapi.resources.borrower.BorrowersResource;
import cpslabteam.bank.server.restapi.resources.branch.BranchAccountResource;
import cpslabteam.bank.server.restapi.resources.branch.BranchAccountsResource;
import cpslabteam.bank.server.restapi.resources.branch.BranchLoanResource;
import cpslabteam.bank.server.restapi.resources.branch.BranchLoansResource;
import cpslabteam.bank.server.restapi.resources.branch.BranchResource;
import cpslabteam.bank.server.restapi.resources.branch.BranchesResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerAccountDepositResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerAccountResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerAccountWithdrawResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerAccountsResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerLoanDepositResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerLoanResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerLoansResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomerResource;
import cpslabteam.bank.server.restapi.resources.customer.CustomersResource;
import cpslabteam.bank.server.restapi.resources.depositor.DepositorResource;
import cpslabteam.bank.server.restapi.resources.depositor.DepositorsResource;
import cpslabteam.bank.server.restapi.resources.loan.LoanBranchResource;
import cpslabteam.bank.server.restapi.resources.loan.LoanOwnerResource;
import cpslabteam.bank.server.restapi.resources.loan.LoanOwnersResource;
import cpslabteam.bank.server.restapi.resources.loan.LoanResource;
import cpslabteam.bank.server.restapi.resources.loan.LoansResource;

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
