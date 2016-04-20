package cpslabteam.bank.server.webserver;

import java.util.Arrays;
import java.util.HashSet;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankWebServer extends Application {

	private static Logger logger = LoggerFactory.getLogger(BankWebServer.class);

	private static Component component;

	private static final BankWebServer singleton = new BankWebServer();

	private static final int SERVER_PORT = 8000;

	private static final String ROOT_ADDRESS = "http://localhost:" + SERVER_PORT;
	
	private static final String DIRECTORY_BASE_PATH = "";
	
	private static final String DIRECTORY_ROOT_URI = "clap://thread/client";
	
	private static final String INDEX_PAGE_NAME = "index";

	public static void close() throws Exception {
		new Thread() {
			public void run() {
				logger.info("Bundle closing, stopping  webserver.");
				try {
					component.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static BankWebServer getInstance() {
		return singleton;
	}

	public static void open() throws Exception {
		new Thread() {
			public void run() {
				try {
					serverStart();
					serverAttach();
					printAddress();
				} catch (InterruptedException e) {
					logger.info("Bundle closing, stopping webserver.");
				}
			}
		}.start();
	}
	
	private static void printAddress(){
		logger.info("You can access the application by addressing {}", ROOT_ADDRESS);
	}

	private static void serverAttach() {
		component.getDefaultHost().attach(getInstance());
	}

	private static void serverStart() throws InterruptedException {
		component = new Component();

		component.getServers().add(Protocol.HTTP, SERVER_PORT);
		component.getClients().add(Protocol.CLAP);

		boolean serverBound = false;
		while (!serverBound) {
			try {
				logger.info("Bundle started,  webserver initializing...");
				component.start();
				if (component.isStarted())
					logger.info("Webserver Started in port {}.", SERVER_PORT);
				serverBound = true;
			} catch (Exception e) {
				try {
					component.stop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.error("Port {} is occupied! Check if a server is running already. Trying again in 15 sec.",
						SERVER_PORT);
				Thread.sleep(15000);
			}
		}
	}

	private BankWebServer() {
		configureCorsService();
	}

	private void configureCorsService() {
		CorsService corsService = new CorsService();
		corsService.setAllowedCredentials(false);
		corsService.setDefaultAllowedMethods(
				new HashSet<Method>(Arrays.asList(Method.GET)));
		corsService.setAllowedOrigins(new HashSet<>(Arrays.asList("*")));
		corsService.setAllowedHeaders(new HashSet<>(Arrays.asList("Content-Type")));
		getServices().add(corsService);
	}

	@Override
	public synchronized Restlet createInboundRoot() {
		final Router router = new Router(getContext());
		Directory directory = new Directory(getContext(), DIRECTORY_ROOT_URI);
		directory.setIndexName(INDEX_PAGE_NAME);
		getMetadataService().addExtension("html", MediaType.TEXT_HTML);
		router.attach(DIRECTORY_BASE_PATH, directory);
		return router;
	}
	
}
