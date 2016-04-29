package cpslab.bank.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.bank.rest.api.BankRestServerActivator;
import cpslab.bank.webserver.BankWebServerActivator;

public class Activator {

	private static Logger logger = LoggerFactory.getLogger(Activator.class);

	public static void main(String[] args) throws Exception {
		BankRestServerActivator.open();
		BankWebServerActivator.open();

		Runtime	.getRuntime()
				.addShutdownHook(new Thread() {

					@Override
					public void run() {
						try {
							BankRestServerActivator.close();
						} catch (Exception e) {
							logger.error("Exception shuting down rest API", e);
						}
						try {
							BankWebServerActivator.close();
						} catch (Exception e) {
							logger.error("Exception shuting down webserver", e);
						}
						super.run();
					}

				});
	}

}
