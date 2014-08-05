package client.ejbadvice.identical.test;

import patterns.ServiceLocator;
import capintro.Advice;
import capintro.AdviceHome;

/**
 * Class to test if two Stateless Session Bean are identical.
 * 
 * Two Stateless Session Bean are identical only when they
 * come from the same home.
 */
public class TestEJBStatelessSessionIdentical {
	public static void main(String[] args) {
		try {
			AdviceHome home1 = (AdviceHome) ServiceLocator.getHome("Advisor", AdviceHome.class);
			AdviceHome home2 = (AdviceHome) ServiceLocator.getHome("Advisor", AdviceHome.class);
			
			Advice adv1 = home1.create();
			Advice adv2 = home1.create();
			
			System.out.println("Testing EJBObjects from the same Home: " + adv1.isIdentical(adv2));
			
			Advice adv3 = home2.create();
			// we get true because (almost always) there will be only one EJBHome in the server
			System.out.println("Testing EJBObjects from the different EJBHome's stub: " + adv2.isIdentical(adv3));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
