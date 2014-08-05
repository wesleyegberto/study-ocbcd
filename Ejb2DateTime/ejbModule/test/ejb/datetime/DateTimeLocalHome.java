package test.ejb.datetime;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * EJB's local home interface.
 */
public interface DateTimeLocalHome extends EJBLocalHome {
	public DateTimeLocal create() throws CreateException;
}
