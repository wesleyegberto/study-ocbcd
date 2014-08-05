package client.ejbadvice.test;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import capintro.Advice;
import capintro.AdviceHome;

public class EJB2AdviceInJ2EETest {
	public static void main(String[] args) {
		try {
			// gets the entry point into the JNDI naming service
			Context ctx = new InitialContext();

			// find the Advice bean using JNDI
			// the returned stub can or cannot implements the home interface (if
			// is non-java)
			Object obj = ctx.lookup("Advisor");

			// prints the got stub
			System.out.println(obj);
			System.out.println(obj.getClass());

			// IIOP Corba - IIOP can transport more information than a plain RMI
			// also let's the server interoperates with other servers, including
			// non-java.
			// The EJB spec says to assume the server is using IIOP, if not the
			// narrow() will do nothing and also we do not need to use it.
			// The narrow() (a exotic cast) takes the received stub and return a
			// stub that
			// really implements the components interface
			obj = PortableRemoteObject.narrow(obj, AdviceHome.class);

			System.out.println(obj.getClass());

			AdviceHome home = (AdviceHome) obj;
			System.out.println(home.getClass());
			
			// create/get the stub to EJBObject that implements the component
			// interface
			Advice advisor = home.create();
			System.out.println(advisor);
			System.out.println("What you  got from J2EE: " + advisor.getMessage());

		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (CreateException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
