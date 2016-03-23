package cpslabteam.bank;

import cpslabteam.bank.webservice.BankRestServerAPI;

public class ServerActivator {

	public static void main(String[] args) throws Exception {
		BankRestServerAPI.open();

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				try {
					BankRestServerAPI.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				super.run();
			}

		});
	}
}
