package cpslab.bank.jsonserialization;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

import capslab.util.db.spi.BaseDataEntity;

/**
 * Serializes Database entity objects or collections of database entity objects.
 * <p>
 * To serialize using this class, the serialization should be done in the
 * context of an active database transaction, since it uses the database
 * connection to read the objects and fields.
 * <p>
 * This class should be used to serialize all database entity objects, since it
 * deals with Lazy initialization.
 *
 */
public class EntityJsonSerializer {

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		hibernate5Module.enable(Feature.FORCE_LAZY_LOADING);
		objectMapper.registerModule(hibernate5Module);
	}

	public static String serialize(BaseDataEntity object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
	
	public static String serialize(Collection<? extends BaseDataEntity> collection) throws JsonProcessingException {
		return objectMapper.writeValueAsString(collection);
	}

	private EntityJsonSerializer() {

	}
}
