package study.projectbank.pattern;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class ServiceLocator {
	private Context context;
	
	private static ServiceLocator serviceLocator;
	
	private ServiceLocator() {
		try {
			context = new InitialContext();
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static ServiceLocator getInstance() {
		if(serviceLocator == null) {
			serviceLocator = new ServiceLocator();
		}
		return serviceLocator;
	}
	
	/**
	 * Do the look up for the EJB Home in the JNDI.
	 * @param jndiName name in the JNDI
	 * @param classEjbHome Class of home interface of the EJB
	 * @return return the EJBHome object narrowed
	 * @throws NamingException
	 */
	public EJBHome getHome(String jndiName, Class classEjbHome) throws NamingException {

		// find the Advice bean using JNDI
		Object obj = context.lookup(jndiName);
		// narrow the obj
		EJBHome ejbHome = (EJBHome) PortableRemoteObject.narrow(obj, classEjbHome);
		
		return ejbHome;
	}
}
