package cpslab.bank.jsonserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

public class BankJsonSerializer {

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		hibernate5Module.enable(Feature.FORCE_LAZY_LOADING);
		objectMapper.registerModule(hibernate5Module);
	}

	public static String serialize(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private BankJsonSerializer() {

	}
}