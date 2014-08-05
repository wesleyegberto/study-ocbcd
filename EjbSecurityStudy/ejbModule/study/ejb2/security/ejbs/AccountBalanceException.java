package study.ejb2.security.ejbs;


public class AccountBalanceException extends Exception {
	private static final long serialVersionUID = 5369265504997338085L;

	public AccountBalanceException() {
		super();
	}

	public AccountBalanceException(String s) {
		super(s);
	}
	
}
