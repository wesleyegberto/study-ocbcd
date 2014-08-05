package study.ejb3.transaction.cmt.stateless;

import javax.ejb.ApplicationException;

/**
 * This exception always will make the Container rolls back the transaction.
 */
@ApplicationException(rollback = true)
public class MyApplicationException extends Exception {
	private static final long serialVersionUID = 3680038934971930072L;

	public MyApplicationException() {
		super();
	}

	public MyApplicationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyApplicationException(String message) {
		super(message);
	}

	public MyApplicationException(Throwable cause) {
		super(cause);
	}

}
