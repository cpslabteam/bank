package cpslabteam.bank.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslabteam.bank.server.restapi.BankRestAPI;
import cpslabteam.bank.server.webserver.BankWebServer;

public class ServerActivator {
	
	private static Logger logger = LoggerFactory.getLogger(ServerActivator.class);

	public static void main(String[] args) throws Exception {
		BankRestAPI.open();
		BankWebServer.open();

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				try {
					BankRestAPI.close();
				} catch (Exception e) {
					logger.error("Exception shuting down rest API", e);
				}
				try{
					BankWebServer.close();
				} catch (Exception e) {
					logger.error("Exception shuting down webserver", e);
				}
				super.run();
			}

		});
	}
}
