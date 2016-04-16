package cpslabteam.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslabteam.bank.webservice.BankRestServerAPI;

public class ServerActivator {
	
	private static Logger logger = LoggerFactory.getLogger(ServerActivator.class);

	public static void main(String[] args) throws Exception {
		BankRestServerAPI.open();

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				try {
					BankRestServerAPI.close();
				} catch (Exception e) {
					logger.error("Exception shuting down server", e);
				}
				super.run();
			}

		});
	}
}
