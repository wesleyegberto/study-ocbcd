package study.ejb3.lifecycle.stateful;

import javax.ejb.Remote;

/**
 * The business interface.
 * Here we should redefine all business method that the bean does.
 * 
 * In EJB 3.x we just need the @Remote annotation to do it.
 */
@Remote
public interface EjbStatefulComponent {
	public void doStuff();
}
