package cpslab.util.rest;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

public class HibernateJacksonConverter extends JacksonConverter {
    protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
        return new HibernateJacksonRepresentation<T>(mediaType, source);
    }

    protected <T> JacksonRepresentation<T> create(Representation source,
         Class<T> objectClass) {
    return new HibernateJacksonRepresentation<T>(source, objectClass);
    }
}