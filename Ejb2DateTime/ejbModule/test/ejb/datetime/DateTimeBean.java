package test.ejb.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * The EJB class with the implementation of the business.
 * Implements the DateTimeBusiness with the business methods.
 */
public class DateTimeBean implements SessionBean, DateTimeBusiness {
	private static final long serialVersionUID = 6456490035773886438L;

	public DateTimeBean() {
		System.out.println("Constructor");
	}
	
	/* Container lifecycle callbacks from SessionBean interface */

	public void setSessionContext(SessionContext sc) {
		System.out.println("setSessionContext");
	}
	
	public void ejbActivate() {
		System.out.println("ejbActivate");
	}

	public void ejbPassivate() {
		System.out.println("ejbPassivate");
	}

	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	
	
	/* Container lifecycle callback from home interface (Stateless EJB must have only this) */
	
	public void ejbCreate() {
		System.out.println("ejbCreate");
	}
	
	
	/* Business methods */
	
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}

	public String getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(new Date());
	}

}
