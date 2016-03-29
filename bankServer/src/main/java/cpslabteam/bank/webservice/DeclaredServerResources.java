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

	static {
		serverResources = new LinkedHashMap<>();
		declareServerResources();
	}

	private static void declareServerResources() {
		serverResources.put("/customers", CustomersResource.class);
		serverResources.put("/customers/{customer}", CustomerResource.class);
		serverResources.put("/depositors", DepositorsResource.class);
		serverResources.put("/depositors/{depositor}", DepositorResource.class);
		serverResources.put("/borrowers", BorrowersResource.class);
		serverResources.put("/borrowers/{borrower}", BorrowerResource.class);
		serverResources.put("/branches", BranchesResource.class);
		serverResources.put("/branches/{branch}", BranchResource.class);
		serverResources.put("/accounts", AccountsResource.class);
		serverResources.put("/accounts/{account}", AccountResource.class);
		serverResources.put("/loans", LoansResource.class);
		serverResources.put("/loans/{loan}", LoanResource.class);
	}

	public static Map<String, Class<? extends ServerResource>> getDeclaredServerResources() {
		return serverResources;
	}

	public static Set<String> getDeclaredServerResourcesURLs() {
		return serverResources.keySet();
	}
}
