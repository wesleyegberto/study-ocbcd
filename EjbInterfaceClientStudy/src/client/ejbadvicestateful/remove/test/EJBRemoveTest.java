package client.ejbadvicestateful.remove.test;

import patterns.ServiceLocator;
import advice.Advice;
import advice.AdviceHome;

public class EJBRemoveTest {
	public static void main(String[] args) {
		try {
			AdviceHome home = (AdviceHome) ServiceLocator.getHome("EJBAdviceStateful",
					AdviceHome.class);
			Advice advisor = home.create("Odair");
			System.out.println(advisor.getMessage());
			
			System.out.println("Removing..");
			
			// We can remove by calling remove(Handle) from home interface or
			// calling remove() on the component interface.
			// After remove, the Container will call ejbRemove() on the bean
			home.remove(advisor.getHandle());
			advisor.remove();
			
			System.out.println("Calling a business method...");
			// Here we will get a java.rmi.NoSuchObjectException CORBA OBJECT_NOT_EXIST 9999
			System.out.println(advisor.getMessage());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
