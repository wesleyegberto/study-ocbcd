package advice;

import java.text.MessageFormat;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AdviceBean implements SessionBean {
	private static final long serialVersionUID = -1365407566658274059L;
	
	private static String[] messages = { "Hello, {0}", "Hi {0}!",
		"What's up, {0}?", "{0}, how are you?", "{0}, do you like Java?",
		"How old are you, {0}?", "{0}! Let's rock!", "{0}, you rocks!" };
	
	
	// fields to client
	private String name;
	
	
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
	
	public void ejbCreate(String name) {
		this.name = name;
		System.out.println("ejbCreate with a name: " + name);
	}
	
	/* Business methods */
	
	public String getMessage() {
		int pos = (int) (Math.random() * messages.length);
		return MessageFormat.format(messages[pos], new Object[]{ name }); 
	}
}
