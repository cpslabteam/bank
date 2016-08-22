package cpslab.bank.rest.handlers.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.util.rest.ErrorStatus;
import cpslab.util.rest.RestService.HTTPErrorCode;
import cpslab.util.rest.ServiceExceptionHandler;

public class IllegalArgumentExceptionHandler implements ServiceExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ErrorStatus handle(Throwable throwable) {
		logger.error("Illegal Argument Exception: " + throwable.getMessage());
		String title = "Illegal Argument";
		String desc = throwable.getMessage();
		return new ErrorStatus(HTTPErrorCode.CLIENT_ERROR_BAD_REQUEST, title, desc);
	}

}
