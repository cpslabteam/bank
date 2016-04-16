package cpslabteam.bank.webservice;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Resource;
import org.restlet.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class BankStatusService extends StatusService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Representation toRepresentation(Status status, Resource resource) {
		String description = null;
		JSONObject ret = new JSONObject();
		if (status.getDescription() != null) {
			description = status.getDescription();
		} else {
			description = "unknown error";
		}
		try {
			ret.put("description", description);
		} catch (JSONException e) {
			logger.error("Error returning exception description", e);
		}
		return new JsonRepresentation(ret);
	}

	@Override
	public Status toStatus(Throwable throwable, Resource resource) {
		Throwable cause = throwable.getCause();

		if (cause == null) {
			return defaultHandleException(throwable, resource);
		} else if (cause instanceof InterruptedException) {
			return defaultHandleException(cause, resource);
		} else if (cause instanceof IOException) {
			return handleIOException(cause, resource);
		} else if (cause instanceof JSONException) {
			return handleJSONException(cause, resource);
		} else if (cause instanceof HibernateException) {
			return handleHibernateException(cause, resource);
		} else {
			return defaultHandleException(cause, resource);
		}
	}

	private Status handleJSONException(Throwable throwable, Resource resource) {
		logger.error("JSONException in " + resource.getReference(), throwable);
		return new Status(Status.CLIENT_ERROR_BAD_REQUEST, throwable, throwable.getMessage(),
				throwable.getLocalizedMessage());
	}

	private Status handleIOException(Throwable throwable, Resource resource) {
		if (throwable instanceof JsonProcessingException) {
			return handleJsonProcessingException(throwable, resource);
		} else {
			return defaultHandleException(throwable, resource);
		}
	}

	private Status handleHibernateException(Throwable throwable, Resource resource) {
		if (throwable instanceof ObjectNotFoundException) {
			ObjectNotFoundException e = (ObjectNotFoundException) throwable;
			logger.error("ObjectNotFoundException in " + resource.getReference(), e);
			return new Status(Status.CLIENT_ERROR_NOT_FOUND, e, e.getLocalizedMessage(),
					"Could not find entity " + e.getEntityName() + " with ID " + e.getIdentifier());
		} else if (throwable instanceof ConstraintViolationException) {
			ConstraintViolationException e = (ConstraintViolationException) throwable;
			logger.error("ConstraintViolationException in " + resource.getReference(), e);
			return new Status(Status.CLIENT_ERROR_CONFLICT, e, e.getConstraintName(),
					"Request violates " + e.getConstraintName() + " constraint");
		} else {
			return defaultHandleException(throwable, resource);
		}
	}

	private Status handleJsonProcessingException(Throwable throwable, Resource resource) {
		if (throwable instanceof JsonMappingException) {
			Throwable cause = throwable.getCause();
			if (cause == null) {
				return defaultHandleException(throwable, resource);
			} else if (cause instanceof HibernateException) {
				return handleHibernateException(cause, resource);
			} else {
				return defaultHandleException(cause, resource);
			}
		} else {
			return defaultHandleException(throwable, resource);
		}
	}

	private Status defaultHandleException(Throwable throwable, Resource resource) {
		logger.error("Exception in " + resource.getReference(), throwable);
		Status status = super.toStatus(throwable, resource);
		return new Status(status, throwable.getLocalizedMessage(), throwable.getMessage());
	}

}
