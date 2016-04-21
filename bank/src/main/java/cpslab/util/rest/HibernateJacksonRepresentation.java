package cpslab.util.rest;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

import cpslab.util.db.hibernate.SessionManager;

public class HibernateJacksonRepresentation<T> extends JacksonRepresentation<T> {
    public HibernateJacksonRepresentation(Representation representation,
                     Class<T> objectClass) {
        super(representation, objectClass);
    }

    public HibernateJacksonRepresentation(T object) {
        super(object);
    }

    public HibernateJacksonRepresentation(MediaType mediaType, T source) {
		super(mediaType, source);
	}

	@Override
    protected ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
		Hibernate5Module hibernate5Module = new Hibernate5Module(SessionManager.getSessionFactory());
		hibernate5Module.enable(Feature.FORCE_LAZY_LOADING);
		objectMapper.registerModule(hibernate5Module);
        return objectMapper;
    }
}