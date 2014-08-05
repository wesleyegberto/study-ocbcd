package study.projectbank.test;

import java.rmi.RemoteException;

import study.projectbank.agency.ejb.AgencyFacade;
import study.projectbank.agency.ejb.AgencyFacadeHome;
import study.projectbank.pattern.ServiceLocator;

public class EjbAgencyTest {
	public static void main(String[] args) {
		try {
			
			AgencyFacadeHome home = (AgencyFacadeHome) ServiceLocator.getInstance().getHome("bank/Agency", AgencyFacadeHome.class);
			
			AgencyFacade agency = home.create(299012);
			System.out.println(agency.getAgencyInformation());
			
			System.out.println("Creating account to Odair");
			agency.createAccount("Odair Jose", 12530);
			System.out.println("Account created");
		} catch(RemoteException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
