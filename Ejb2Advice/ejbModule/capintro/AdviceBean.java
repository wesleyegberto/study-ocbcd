package capintro;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AdviceBean implements SessionBean {
	private static final long serialVersionUID = -1365407566658274059L;
	
	/* Container lifecycle callbacks from SessionBean interface */

	public void ejbActivate() {
		System.out.println("ejbActivate");
	}

	public void ejbPassivate() {
		System.out.println("ejbPassivate");
	}

	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	public void setSessionContext(SessionContext sc) {
		System.out.println("setSessionContext");
	}

	/* Container lifecycle callback from home interface (Stateless EJB must have only this) */
	
	public void ejbCreate() {
		System.out.println("ejbCreate");
	}
	
	/* Business methods */
	
	public String getMessage() {
		return "It's work!";
	}
	
}
