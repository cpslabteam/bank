package cpslabteam.bank.webservice;

import java.util.Arrays;
import java.util.HashSet;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

public final class BankRestServerAPI extends Application {

	private static Component component;

	private static final String LOGGER_TAG = "[Bank REST API] ";

	private static final BankRestServerAPI singleton = new BankRestServerAPI();

	private static final int SERVER_PORT = 9192;

	private static final String ROOT_ADDRESS = "http://localhost:" + SERVER_PORT;

	public static void close() throws Exception {
		new Thread() {
			public void run() {
				logInfo("Bundle closing, stopping REST server.");
				try {
					component.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static BankRestServerAPI getInstance() {
		return singleton;
	}

	public static void open() throws Exception {
		new Thread() {
			public void run() {
				try {
					serverStart();
					serverAttach();
					printAvailableEndpoints();
				} catch (InterruptedException e) {
					logInfo("Bundle closing, stopping REST server.");
				}
			}
		}.start();
	}

	private static void logError(String errorMessage) {
		System.out.println("[ERROR]" + LOGGER_TAG + errorMessage);
	}

	private static void logInfo(String infoMessage) {
		System.out.println("[INFO]" + LOGGER_TAG + infoMessage);
	}

	private static void printAvailableEndpoints() {
		logInfo("You can use this REST API by addressing the following endpoints:");
		DeclaredServerResources.getDeclaredServerResourcesURLs().forEach(url -> logInfo("	- " + ROOT_ADDRESS + url));
	}

	private static void serverAttach() {
		component.getDefaultHost().attach(getInstance());
	}

	private static void serverStart() throws InterruptedException {
		component = new Component();

		component.getServers().add(Protocol.HTTP, SERVER_PORT);

		boolean serverBound = false;
		while (!serverBound) {
			try {
				logInfo("Bundle started, REST server initializing...");
				component.start();
				if (component.isStarted())
					logInfo("REST Server Started in port " + SERVER_PORT + ".");
				serverBound = true;
			} catch (Exception e) {
				try {
					component.stop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logError("Port " + SERVER_PORT
						+ " is occupied! Check if a server is running already. Trying again in 15 sec.");
				Thread.sleep(15000);
			}
		}
	}

	private BankRestServerAPI() {
		configureCorsService();
		setStatusService(new BankStatusService());
	}

	private void configureCorsService() {
		CorsService corsService = new CorsService();
		corsService.setAllowedCredentials(false);
		corsService.setDefaultAllowedMethods(
				new HashSet<Method>(Arrays.asList(Method.GET, Method.PUT, Method.POST, Method.DELETE, Method.OPTIONS)));
		corsService.setAllowedOrigins(new HashSet<>(Arrays.asList("*")));
		corsService.setAllowedHeaders(new HashSet<>(Arrays.asList("Content-Type")));
		getServices().add(corsService);
	}

	@Override
	public synchronized Restlet createInboundRoot() {
		final Router router = new Router(getContext());
		DeclaredServerResources.getDeclaredServerResources().forEach((url, resource) -> router.attach(url, resource));
		return router;
	}
}
