package client.ejbdatetime;

import patterns.ServiceLocator;
import test.ejb.datetime.DateTime;
import test.ejb.datetime.DateTimeHome;

public class EJBDateTimeTest {
	public static void main(String[] args) {
		try {
			DateTimeHome home = (DateTimeHome) ServiceLocator.getHome("EJBDateTime", DateTimeHome.class);
		
			DateTime dt = home.create();
			
			System.out.println("Date: " + dt.getDate());
			System.out.println("Time: " + dt.getTime());
			System.out.println("Date and Time: " + dt.getTimestamp());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
