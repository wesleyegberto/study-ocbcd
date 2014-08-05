package patterns;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * Class that implements the Service Locator J2EE Pattern.
 */
public class ServiceLocator {
	
	/**
	 * Do the look up for the EJB Home in the JNDI.
	 * @param jndiName name in the JNDI
	 * @param classEjbHome Class of home interface of the EJB
	 * @return return the EJBHome object narrowed
	 * @throws NamingException
	 */
	public static EJBHome getHome(String jndiName, Class classEjbHome) throws NamingException {
		// gets the entry point into the JNDI naming service
		Context ctx = new InitialContext();

		// find the Advice bean using JNDI
		Object obj = ctx.lookup(jndiName);
		// narrow the obj
		EJBHome ejbHome = (EJBHome) PortableRemoteObject.narrow(obj, classEjbHome);
		
		return ejbHome;
	}
}
