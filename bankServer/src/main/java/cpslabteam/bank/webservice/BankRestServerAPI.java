package cpslabteam.bank.webservice;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Header;
import org.restlet.data.Protocol;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;
import org.restlet.util.Series;

public final class BankRestServerAPI extends Application{

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
					logInfo("Initializing REST server.");
					serverStart();
					serverAttach();
					printAvailableEndpoints();
					logInfo("Server started!");
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
		BankServices.getDeclaredServicesURLs().forEach( url -> logInfo("	->" + ROOT_ADDRESS + url));
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
				logError("Port " + SERVER_PORT + " is occupied! Trying again in 15 sec.");
				Thread.sleep(15000);
			}
		}
	}
	
	private BankRestServerAPI() {
	}
	
	@Override
	public synchronized Restlet createInboundRoot() {
		final Router router = new Router(getContext());
		BankServices.getDeclaredServices().forEach((url, resource) -> router.attach(url, resource));
		return createCorsFilter(router);
	}
	
	private Filter createCorsFilter(Restlet router) {
		Filter filter = new Filter(getContext(), router) {
			@SuppressWarnings("unchecked")
			@Override
			protected int beforeHandle(Request request, Response response) {
				Series<Header> responseHeaders = (Series<Header>) response.getAttributes()
						.get(HeaderConstants.ATTRIBUTE_HEADERS);
				if (responseHeaders == null) {
					responseHeaders = new Series<Header>(Header.class);
				}

				Series<Header> requestHeaders = (Series<Header>) request.getAttributes()
						.get(HeaderConstants.ATTRIBUTE_HEADERS);
				String requestOrigin = requestHeaders.getFirstValue("Origin", false, "*");

				responseHeaders.set("Access-Control-Allow-Credentials", "false");
				responseHeaders.set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
				responseHeaders.set("Access-Control-Max-Age", "60");
				responseHeaders.set("Access-Control-Allow-Origin", requestOrigin);
				responseHeaders.set("Access-Control-Allow-Headers", "Content-Type");

				response.getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, responseHeaders);

				if (org.restlet.data.Method.OPTIONS.equals(request.getMethod())) {
					return Filter.STOP;
				}
				return super.beforeHandle(request, response);
			}
		};
		return filter;
	}
}
