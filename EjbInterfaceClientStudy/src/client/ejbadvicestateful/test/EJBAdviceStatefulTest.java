package client.ejbadvicestateful.test;

import patterns.ServiceLocator;
import advice.Advice;
import advice.AdviceHome;

public class EJBAdviceStatefulTest {
	public static void main(String[] args) {
		try {
			AdviceHome home = (AdviceHome) ServiceLocator.getHome("EJBAdviceStateful",
					AdviceHome.class);
			Advice advisor = home.create("Odair");
			
			System.out.println(advisor.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
