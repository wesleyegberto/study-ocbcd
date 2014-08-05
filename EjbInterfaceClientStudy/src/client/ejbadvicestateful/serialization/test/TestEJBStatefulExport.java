package client.ejbadvicestateful.serialization.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import patterns.ServiceLocator;
import advice.Advice;
import advice.AdviceHome;

/**
 * Test the serialization of the EJBObject's stub using the Handle object.
 * 
 * A handle is a serializable object that knows how to get back to the
 * original Remote EJB object (it's now a subt, but it can get the stub).
 * This handle has the "smarts" to communicate with the server and get back
 * something that is just the same as the EJB object you had before (might not
 * be the same EJB object, but the client will never be able to tell the difference).
 */
public class TestEJBStatefulExport {
	public static void main(String[] args) {
		ObjectOutputStream oos = null;
		try {
			// get the home
			advice.AdviceHome home = (AdviceHome) ServiceLocator.getHome("EJBAdviceStateful", AdviceHome.class);
			
			// create a EJBObject
			Advice advisor = home.create("Odair");
			System.out.println("Advisor got: " + advisor);
			
			// create the output stream to export the stub
			oos = new ObjectOutputStream(new FileOutputStream("advice_stateful_handle.dat"));
			// write the stub's handle
			oos.writeObject(advisor.getHandle());
			oos.flush();
			
			System.out.println("Exported!");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch(IOException e) {
				}
			}
		}

	}
}
