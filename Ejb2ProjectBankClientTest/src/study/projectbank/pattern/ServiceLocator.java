package study.projectbank.pattern;

import java.util.Hashtable;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class ServiceLocator {
	private Context context;
	
	private static ServiceLocator serviceLocator;
	private Hashtable jndiProps = new Hashtable();
	
	private ServiceLocator() {
		// loads the Class manually beacuse J2EE isn't loading
		/*try {
			//Class.forName("org.gjt.mm.mysql.Driver");
			//Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e1) {
			System.out.println("Test: Driver not found.");
		}*/
		jndiProps.put(Context.PROVIDER_URL, "jnp://localhost:1050/");
		
		try {
			context = new InitialContext(jndiProps);
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
	//@SuppressWarnings("rawtypes")
	public EJBHome getHome(String jndiName, Class classEjbHome) throws NamingException {

		// find the Advice bean using JNDI
		Object obj = context.lookup(jndiName);
		// narrow the obj
		EJBHome ejbHome = (EJBHome) PortableRemoteObject.narrow(obj, classEjbHome);
		
		return ejbHome;
	}
}
