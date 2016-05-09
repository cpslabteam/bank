package cpslab.bank.rest.services;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONException;

import cpslab.bank.rest.handlers.hibernate.ConstraintViolationExceptionHandler;
import cpslab.bank.rest.handlers.hibernate.ObjectNotFoundExceptionHandler;
import cpslab.bank.rest.handlers.json.JSONExceptionHandler;
import cpslab.util.rest.RestJSONServicesProvider;

public class BaseServerResource extends RestJSONServicesProvider {

	public BaseServerResource() {
		super();
		registerExceptionHandler(ResourceMethod.values(), ObjectNotFoundException.class,
				ObjectNotFoundExceptionHandler.class);
		registerExceptionHandler(ResourceMethod.values(), ConstraintViolationException.class,
				ConstraintViolationExceptionHandler.class);
		registerExceptionHandler(ResourceMethod.values(), JSONException.class,
				JSONExceptionHandler.class);
	}
}
