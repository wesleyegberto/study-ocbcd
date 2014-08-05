package study.ejb3.advice;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
public class AdviceBean implements Advice {

	/* Container lifecycle callbacks (like those in SessionBean interface) */

	@Resource
	public void setSessionContext(SessionContext sc) {
		System.out.println("setSessionContext");
	}
	
	@Remove
	public void remove() {
		System.out.println("ejbRemove");
	}
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("ejbCreate");
	}

	/* Business methods */
	public String getMessage() {
		return "It's work!";
	}

}
