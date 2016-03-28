package cpslabteam.bank.webservice;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Resource;
import org.restlet.service.StatusService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class BankStatusService extends StatusService {

	@Override
	public Representation toRepresentation(Status status, Resource resource) {
		String ret = null;
		if (status.getDescription() != null) {
			ret = status.getDescription();
		} else {
			ret = "unknown error";
		}
		return new StringRepresentation(ret, MediaType.APPLICATION_JSON);
	}

	@Override
	public Status toStatus(Throwable throwable, Resource resource) {
		Throwable cause = throwable.getCause();

		if (cause == null) {
			return handleUnknownException(throwable, resource);
		} else if (cause instanceof JsonProcessingException) {
			return handleJsonProcessingException(cause, resource);
		} else if (cause instanceof HibernateException) {
			return handleHibernateException(cause, resource);
		} else {
			return handleUnknownException(cause, resource);
		}
	}

	private Status handleHibernateException(Throwable throwable, Resource resource) {
		if (throwable instanceof ObjectNotFoundException) {
			return new Status(Status.CLIENT_ERROR_BAD_REQUEST, throwable, throwable.getLocalizedMessage(),
					throwable.getMessage());
		} else if (throwable instanceof ConstraintViolationException) {
			return new Status(Status.CLIENT_ERROR_BAD_REQUEST, throwable, throwable.getLocalizedMessage(),
					throwable.getMessage());
		} else {
			return handleUnknownException(throwable, resource);
		}
	}

	private Status handleJsonProcessingException(Throwable throwable, Resource resource) {
		if (throwable instanceof JsonMappingException) {
			Throwable cause = throwable.getCause();
			if (cause == null) {
				return handleUnknownException(throwable, resource);
			} else if (cause instanceof HibernateException) {
				return handleHibernateException(throwable, resource);
			} else {
				return handleUnknownException(cause, resource);
			}
		} else {
			return handleUnknownException(throwable, resource);
		}
	}
	
	private Status handleUnknownException(Throwable throwable, Resource resource){
		Status status = super.toStatus(throwable, resource);
		return new Status(status, throwable.getLocalizedMessage(), throwable.getMessage());
	}

}
