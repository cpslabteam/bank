package cpslab.bank.rest.handlers.hibernate;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.util.rest.ErrorStatus;
import cpslab.util.rest.RestService.HTTPErrorCode;
import cpslab.util.rest.ServiceExceptionHandler;

public class ObjectNotFoundExceptionHandler implements ServiceExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ErrorStatus handle(Throwable throwable) {
		ObjectNotFoundException e = (ObjectNotFoundException) throwable;
		logger.error("ObjectNotFoundException for entity " + e.getEntityName() + " with id "
				+ e.getIdentifier());
		String title = "Entity not found";
		String desc =
				"Could not find entity " + e.getEntityName() + " with ID " + e.getIdentifier();
		ErrorStatus errorStatus =
				new ErrorStatus(HTTPErrorCode.CLIENT_ERROR_NOT_FOUND, title, desc);
		return errorStatus;
	}

}
