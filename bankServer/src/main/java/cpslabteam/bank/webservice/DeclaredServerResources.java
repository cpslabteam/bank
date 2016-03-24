package cpslabteam.bank.webservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.ServerResource;

import cpslabteam.bank.webservice.resources.CustomerResource;
import cpslabteam.bank.webservice.resources.CustomersResource;

public final class DeclaredServerResources {

	private static Map<String, Class<? extends ServerResource>> serverResources;

	static {
		serverResources = new HashMap<>();
		declareServerResources();
	}

	private static void declareServerResources() {
		serverResources.put("/customers", CustomersResource.class);
		serverResources.put("/customers/{customer}", CustomerResource.class);
	}

	public static Map<String, Class<? extends ServerResource>> getDeclaredServerResources() {
		return serverResources;
	}

	public static Set<String> getDeclaredServerResourcesURLs() {
		return serverResources.keySet();
	}
}
