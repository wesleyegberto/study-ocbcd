package study.ejb3.projectbank.agency;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AgencyException extends Exception {
	private static final long serialVersionUID = 8786415911515961496L;

	public AgencyException() {
		super();
	}

	public AgencyException(String s) {
		super(s);
	}

}
