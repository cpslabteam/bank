package cpslabteam.bank.webservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.ServerResource;

public final class BankServices {

	private static Map<String, Class<? extends ServerResource>> services;

	static {
		services = new HashMap<>();
		declareServices();
	}

	private static void declareServices() {
		services.put("/url", ServerResource.class);
	}

	public static Map<String, Class<? extends ServerResource>> getDeclaredServices() {
		return services;
	}

	public static Set<String> getDeclaredServicesURLs() {
		return services.keySet();
	}
}
