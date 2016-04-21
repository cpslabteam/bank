package cpslab.bank.server.restapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.bank.jsonserialization.HibernateJacksonConverter;

public final class BankRestAPI extends Application {

	private static Logger logger = LoggerFactory.getLogger(BankRestAPI.class);

	private static Component component;

	private static final BankRestAPI singleton = new BankRestAPI();

	private static final int SERVER_PORT = 9192;

	private static final String ROOT_ADDRESS = "http://localhost:" + SERVER_PORT;

	public static void close() throws Exception {
		new Thread() {
			public void run() {
				logger.info("Bundle closing, stopping REST server.");
				try {
					component.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static BankRestAPI getInstance() {
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
					logger.info("Bundle closing, stopping REST server.");
				}
			}
		}.start();
	}

	private static void printAvailableEndpoints() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("You can use this REST API by addressing the following endpoints:\n");
		List<String> endpoints = new ArrayList<>();
		Set<String> serverResourcesURLs = DeclaredServerResources.getDeclaredServerResourcesURLs();
		for(String url : serverResourcesURLs){
			stringBuilder.append("	"+ROOT_ADDRESS+"{}\n");
			endpoints.add(url);
		}
		logger.info(stringBuilder.toString(), endpoints.toArray());
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
				logger.info("Bundle started, REST server initializing...");
				component.start();
				if (component.isStarted())
					logger.info("REST Server Started in port {}.", SERVER_PORT);
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

	private BankRestAPI() {
		configureCorsService();
		setStatusService(new BankStatusService());
		setHibernateJacksonConverter();
	}

	private void setHibernateJacksonConverter() {
		List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
		JacksonConverter jacksonConverter = new JacksonConverter();
		for (ConverterHelper converter : converters) {
			if (converter instanceof JacksonConverter) {
				jacksonConverter = (JacksonConverter) converter;
				break;
			}
		}
		if (jacksonConverter != null) {
			converters.remove(jacksonConverter);
			converters.add(new HibernateJacksonConverter());
		}
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
