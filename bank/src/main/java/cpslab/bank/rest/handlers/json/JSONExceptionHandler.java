package cpslab.bank.rest.handlers.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpslab.bank.rest.handlers.BaseHandler;
import cpslab.util.rest.ErrorStatus;
import cpslab.util.rest.RestService.HTTPErrorCode;
import cpslab.util.rest.ServiceExceptionHandler;

public class JSONExceptionHandler extends BaseHandler implements ServiceExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private final String PROPERTY_NOT_FOUND_PATTERN = ".+\\[\"(?<property>.+)\"\\] not found.+";

	@Override
	public ErrorStatus handle(Throwable throwable) {
		rollbackTransactionIfActive();
		logger.error("JSONException: " + throwable.getMessage());
		String description;
		Matcher m = Pattern.compile(PROPERTY_NOT_FOUND_PATTERN).matcher(throwable.getMessage());
		if (m.matches()) {
			description = String.format("Property %s is required", m.group("property"));
		} else {
			description = throwable.getMessage();
		}
		return new ErrorStatus(HTTPErrorCode.CLIENT_ERROR_BAD_REQUEST, "Json exception",
				description);
	}

}
