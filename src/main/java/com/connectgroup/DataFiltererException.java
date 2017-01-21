package com.connectgroup;

/**
 * RuntimeException suitable for capturing any uncaught exceptions during
 * filtering.
 * 
 * It will be expected that this RTE will be caught by a client / container /
 * Uncaught Exception Handler.
 * 
 * Unfortunately DataFilterer does not permit a change of method signatures so
 * unable to throw any checked exceptions. An option would be to consume the
 * checked exception and log the exception or use this RTE to wrap the checked
 * exception.
 * 
 * 
 * @author Mark Hawkins
 *
 */
public class DataFiltererException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8173708079351707201L;

	/**
	 * RuntimeException constructor.
	 * 
	 * @param message
	 *            String the message of interest.
	 * @param exception
	 *            Throwable
	 */
	public DataFiltererException(final String message, final Throwable exception) {
		super("DataFilterer Exception - " + message, exception);
	}

}
