package study.ejb3.projectbank.account;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AccountException extends Exception {
	private static final long serialVersionUID = 8786415911515961496L;

	public AccountException() {
		super();
	}

	public AccountException(String s) {
		super(s);
	}

}
