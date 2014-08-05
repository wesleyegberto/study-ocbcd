package study.ejb2.security.ejbs;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface AccountHome extends EJBLocalHome {
	public AccountComponent create(String account, String name) throws CreateException;
	public AccountComponent create(String name, double balance) throws CreateException;
}
