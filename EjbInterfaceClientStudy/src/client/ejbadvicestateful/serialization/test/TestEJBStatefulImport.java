package client.ejbadvicestateful.serialization.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.ejb.Handle;
import javax.rmi.PortableRemoteObject;

import advice.Advice;

public class TestEJBStatefulImport {
	public static void main(String[] args) {
		ObjectInputStream ois = null;
		try {
			// create the input stream of the file
			ois = new ObjectInputStream(new FileInputStream("advice_stateful_handle.dat"));

			// reads the serialized object and cast to java.ejb.Handle
			Handle handle = (Handle) ois.readObject();
			
			// get and narrow the previous EJBObject (might get a RemoteException if the EJBObject
			// is already deleted by the Container)
			Advice advisor = (Advice) PortableRemoteObject.narrow(handle.getEJBObject(), Advice.class);
			System.out.println("Imported!");
			
			System.out.println("Advisor got: " + advisor);
			System.out.println(advisor.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch(IOException e) {
				}
			}
		}

	}
}
