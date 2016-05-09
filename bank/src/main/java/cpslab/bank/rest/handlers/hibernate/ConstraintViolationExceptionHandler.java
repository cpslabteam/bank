package cpslab.bank.rest.handlers.hibernate;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.bank.rest.handlers.BaseHandler;
import cpslab.util.rest.ErrorStatus;
import cpslab.util.rest.RestService.HTTPErrorCode;
import cpslab.util.rest.ServiceExceptionHandler;

public class ConstraintViolationExceptionHandler extends BaseHandler
		implements ServiceExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ErrorStatus handle(Throwable throwable) {
		rollbackTransactionIfActive();
		ConstraintViolationException e = (ConstraintViolationException) throwable;
		logger.error("ConstraintViolationException for constraint " + e.getConstraintName());
		String title = "Constraint violation";
		String desc = "Request violates " + e.getConstraintName() + " constraint";
		ErrorStatus errorStatus = new ErrorStatus(HTTPErrorCode.CLIENT_ERROR_CONFLICT, title, desc);
		return errorStatus;
	}

}
