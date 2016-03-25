package cpslabteam.bank.jsonserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

import cpslabteam.bank.database.utils.SessionManager;

public class BankJsonSerializer {

	public static String serializeDetails(Object object) throws JsonProcessingException {
		return serialize(JsonViews.Details.class, object);
	}

	public static String serializeInfo(Object object) throws JsonProcessingException {
		return serialize(JsonViews.Info.class, object);
	}

	private static String serialize(Class<?> view, Object object) throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper()
				.registerModule(
						new Hibernate5Module(SessionManager.getSessionFactory()).enable(Feature.FORCE_LAZY_LOADING))
				.writerWithView(view);
		String response = objectWriter.writeValueAsString(object);
		return response;
	}

	private BankJsonSerializer() {

	}
}
