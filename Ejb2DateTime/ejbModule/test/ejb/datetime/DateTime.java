package test.ejb.datetime;

import javax.ejb.EJBObject;

/**
 * EJB's component interface.
 * Extends the DateTimeBusiness with the business methods.
 */
public interface DateTime extends EJBObject, DateTimeBusinessRemote {
	// all methods are declared in the DateTimeBusinessRemote
}
