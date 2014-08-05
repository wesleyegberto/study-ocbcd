package client.ejbadvicestateful.passivation.test;

import patterns.ServiceLocator;
import advice.Advice;
import advice.AdviceHome;

public class EJBPassivationTest {
	public static void main(String[] args) {
		try {
			final int sizeHome = 10;
			final int size = 1000;
			
			AdviceHome[] homes = new AdviceHome[sizeHome];
			Advice[] beans = new Advice[size];
			
			for(int j = 0; j < sizeHome; j++) {
				homes[j] = (AdviceHome) ServiceLocator.getHome("EJBAdviceStateful", AdviceHome.class);
				for(int i = 0; i < size; i++) {
					beans[i] = homes[j].create("Client " + j + i);
					System.out.println(beans[i].getMessage());
				}
			}
			Thread.sleep(5 * 60000);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
